package com.taglessfinal

import cats._
import cats.data.{NonEmptyList, Validated}
import cats.implicits._

import scala.util.Try

object validator {
  trait UserValidator[F[_]] {
    def validateName(user: User): F[String]
    def validateAge(user: User): F[Int]
    def validatePhone(user: User): F[String]
    def validateEmail(user: User): F[String]
    def validate(user:User):F[User]
  }

  object UserValidator {
    def apply[F[_]](implicit ev: UserValidator[F]): UserValidator[F] = ev
    def validateName[F[_] : UserValidator, E](user: User): F[String] = UserValidator[F].validateName(user)
    def validateAge[F[_] : UserValidator, E](user: User): F[Int] = UserValidator[F].validateAge(user)
    def validatePhone[F[_] : UserValidator, E](user: User): F[String] = UserValidator[F].validatePhone(user)
    def validateEmail[F[_] : UserValidator, E](user: User): F[String] = UserValidator[F].validateEmail(user)

    def validate[F[_]: UserValidator, E](user:User):F[User] = UserValidator[F].validate(user)
  }

  def userValidator[F[_], E](mkError: UserError => E)
                            (implicit A: ApplicativeError[F, E]): UserValidator[F] = new UserValidator[F] {
    def validateName(user: User): F[String] =
      if (user.name.matches("(?i:^[a-z][a-z ,.'-]*$)"))
        user.name.pure[F]
      else A.raiseError(mkError(InvalidName))

    def validatePhone(user: User): F[String] =
      if (user.phone.matches("^[1-9]\\d{2}-\\d{3}-\\d{4}"))
        user.phone.pure[F]
      else A.raiseError(mkError(InvalidPhoneNumber))

    def validateAge(user: User): F[Int] =
      if (user.age >= 18 && user.age < 120) user.age.pure[F]
      else A.raiseError(mkError(InvalidAge))

    def validateEmail(user: User): F[String] =
      if (user.email.matches("^\\S+@\\S+$")) user.email.pure[F]
      else A.raiseError(mkError(InvalidEmail))


    def validate(user: User): F[User] = {
      (User.apply _).curried.pure[F] <*>
        validateName(user) <*>
        validatePhone(user) <*>
        validateEmail(user) <*>
        validateAge(user)
    }
  }
  implicit class UserValidatorOption(user:User) {
    def validate:Option[User] = {
      val interpreter = userValidator[Option, Unit](_ => ())
      interpreter.validate(user)
    }
  }
  implicit class UserValidatorTry(user:User) {
    def validate:Try[User] = {
      val interpreter = userValidator[Try, Throwable](err => new Throwable(err.toString))
      interpreter.validate(user)
    }
  }
  implicit class UserValidatorEither(user:User) {
    def validate:Either[UserError, User] = {
      val interpreter = userValidator[Either[UserError, *], UserError](identity)
      interpreter.validate(user)
    }
  }
  implicit class UserValidatorValidated(user:User) {
    def validate:Validated[NonEmptyList[UserError], User] = {
      val interpreter = userValidator[Validated[NonEmptyList[UserError], *], NonEmptyList[UserError]](NonEmptyList(_, Nil))
      interpreter.validate(user)
    }
  }
}


