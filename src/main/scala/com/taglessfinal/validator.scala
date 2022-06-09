package com.taglessfinal

import cats._
import cats.data.{NonEmptyList, Validated}
import cats.implicits._

import scala.util.Try

object validator {
  trait UserValidator[F[_]] {
    def validate(user:User):F[User]
  }

//  object UserValidator {
//    def apply[F[_]](implicit ev: UserValidator[F]): UserValidator[F] = ev
//  }

  def userValidator[F[_], E](mkError: UserError => E)
                            (implicit A: ApplicativeError[F, E]): UserValidator[F] = new UserValidator[F] {
    def validateName(user: User): F[Name] =
      if (user.name.value.matches("(?i:^[a-z][a-z ,.'-]*$)"))
        user.name.pure[F]
      else A.raiseError(mkError(InvalidName))

    def validatePhone(user: User): F[Phone] =
      if (user.phone.value.matches("^[1-9]\\d{2}-\\d{3}-\\d{4}"))
        user.phone.pure[F]
      else A.raiseError(mkError(InvalidPhoneNumber))

    def validateAge(user: User): F[Age] =
      if (user.age.value >= 18 && user.age.value < 120) user.age.pure[F]
      else A.raiseError(mkError(InvalidAge))

    def validateEmail(user: User): F[Email] =
      if (user.email.value.matches("^\\S+@\\S+$")) user.email.pure[F]
      else A.raiseError(mkError(InvalidEmail))


    def validate(user: User): F[User] = {
      (User.apply _).curried.pure[F] <*>
        validateName(user) <*>
        validatePhone(user) <*>
        validateEmail(user) <*>
        validateAge(user)
    }
  }
  implicit class userValidatorOptionInterpreter(user:User) {
    def validate:Option[User] = userValidator[Option, Unit](_ => ())
      .validate(user)
  }
  implicit class userValidatorTryInterpreter(user:User) {
    def validate:Try[User] = userValidator[Try, Throwable](err => new Throwable(err.toString))
      .validate(user)

  }
  implicit class userValidatorEitherInterpreter(user:User) {
    def validate:Either[UserError, User] = userValidator[Either[UserError, *], UserError](identity)
      .validate(user)
  }
  implicit class userValidatorValidatedInterpreter(user:User) {
    def validate:Validated[NonEmptyList[UserError], User] =
      userValidator[Validated[NonEmptyList[UserError], *], NonEmptyList[UserError]](NonEmptyList(_, Nil))
      .validate(user)
  }
}


