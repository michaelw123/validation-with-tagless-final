package com.taglessfinal

import cats._
import cats.data.{NonEmptyList, Validated}
import cats.implicits._

import scala.util.Try

object validator {
  trait Validator[F[_], A] {
    def validate:F[A]
  }
  trait UserValidator[F[_]] extends  Validator[F, User] {
    def validate:F[User]
  }
  case class UserValidatioInterpreter[F[_], E](mkError: UserError => E)(implicit A: ApplicativeError[F, E]) {
    def validateName(name: Name)(implicit A: ApplicativeError[F, E]): F[Name] =
      if (name.value.matches("(?i:^[a-z][a-z ,.'-]*$)"))
        name.pure[F]
      else A.raiseError(mkError(InvalidName))

    def validatePhone(phone: Phone)(implicit A: ApplicativeError[F, E]): F[Phone] =
      if (phone.value.matches("^[1-9]\\d{2}-\\d{3}-\\d{4}"))
        phone.pure[F]
      else A.raiseError(mkError(InvalidPhoneNumber))

    def validateAge(age: Age)(implicit A: ApplicativeError[F, E]): F[Age] =
      if (age.value >= 18 && age.value < 120) age.pure[F]
      else A.raiseError(mkError(InvalidAge))

    def validateEmail(email: Email)(implicit A: ApplicativeError[F, E]): F[Email] =
      if (email.value.matches("^\\S+@\\S+$")) email.pure[F]
      else A.raiseError(mkError(InvalidEmail))

    def interpreter: User => F[User] = { user => {
        (User.apply _).curried.pure[F] <*>
          validateName(user.name) <*>
          validatePhone(user.phone) <*>
          validateEmail(user.email) <*>
          validateAge(user.age)
      }
    }
  }

  implicit class userValidatorOptionInterpreter(user:User) extends UserValidator[Option] {
    val interpreter = UserValidatioInterpreter[Option, Unit](_ => ()).interpreter
    override def validate = interpreter(user)
  }
  implicit class userValidatorTryInterpreter(user:User) extends UserValidator[Try]  {
    val interpreter = UserValidatioInterpreter[Try, Throwable](err => new Throwable(err.toString)).interpreter
    override def validate = interpreter(user)

  }
  implicit class userValidatorEitherInterpreter(user:User) extends UserValidator[Either[UserError, *]]  {
    val interpreter = UserValidatioInterpreter[Either[UserError, *], UserError](identity).interpreter
    override def validate = interpreter(user)

  }
  implicit class userValidatorValidatedInterpreter(user:User) extends UserValidator[Validated[NonEmptyList[UserError], *]]{
    val interpreter = UserValidatioInterpreter[Validated[NonEmptyList[UserError], *], NonEmptyList[UserError]](NonEmptyList(_, Nil)).interpreter
    override def validate = interpreter(user)
  }
}


