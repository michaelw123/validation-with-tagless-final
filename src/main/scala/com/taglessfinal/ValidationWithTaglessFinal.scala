package com.taglessfinal

import cats._
import cats.implicits._

object ValidationWithTaglessFinal {
  trait UserValidator[F[_]] {
    def validate(user:User):F[User]
  }

  object UserValidator {
    def apply[F[_]](implicit ev: UserValidator[F]): UserValidator[F] = ev
    def validate[F[_]: UserValidator, E](user:User) = UserValidator[F].validate(user)
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
}


