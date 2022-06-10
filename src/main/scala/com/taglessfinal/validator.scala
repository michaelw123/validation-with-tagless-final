package com.taglessfinal

import cats._
import cats.data.{NonEmptyList, Validated}
import cats.implicits._

import scala.util.Try

object validator {
  trait UserValidator[F[_]] {
    def validate(user:User):F[User]
  }

  def userValidator[F[_], E](mkError: UserError => E)
                            (implicit A: ApplicativeError[F, E]): UserValidator[F] = new UserValidator[F] {
    def validateName(name: Name): F[Name] =
      if (name.value.matches("(?i:^[a-z][a-z ,.'-]*$)"))
        name.pure[F]
      else A.raiseError(mkError(InvalidName))

    def validatePhone(phone: Phone): F[Phone] =
      if (phone.value.matches("^[1-9]\\d{2}-\\d{3}-\\d{4}"))
        phone.pure[F]
      else A.raiseError(mkError(InvalidPhoneNumber))

    def validateAge(age: Age): F[Age] =
      if (age.value >= 18 && age.value < 120) age.pure[F]
      else A.raiseError(mkError(InvalidAge))

    def validateEmail(email: Email): F[Email] =
      if (email.value.matches("^\\S+@\\S+$")) email.pure[F]
      else A.raiseError(mkError(InvalidEmail))


    def validate(user: User): F[User] = {
      (User.apply _).curried.pure[F] <*>
        validateName(user.name) <*>
        validatePhone(user.phone) <*>
        validateEmail(user.email) <*>
        validateAge(user.age)
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


