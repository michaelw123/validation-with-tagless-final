package com.taglessfinal

import cats._
import cats.data._
import cats.implicits._
import cats.data.Validated._

import scala.util.Try


object validator {
  trait UserValidator[F[_]] {
    def createValidUser(firstname: String, lastname: String, age: Int): F[User]
  }

  object UserValidator {
    def apply[F[_]](implicit ev: UserValidator[F]): UserValidator[F] = ev
    def validate[F[_] : UserValidator, E](firstname: String, lastname: String, age: Int): F[User] =
      UserValidator[F].createValidUser(firstname, lastname, age)
  }

  val userValidatorIdInterpreter = new UserValidator[Id] {
    def createValidUser(firstname: String, lastname: String, age: Int): Id[User]
      = User(firstname, lastname, age)
  }
  implicit val userValidatorInterpreter = userValidatorIdInterpreter

  def userValidator[F[_], E](mkError: UserError => E)
                            (implicit A: ApplicativeError[F, E]): UserValidator[F] = new UserValidator[F] {
      def validateName(name: String): F[String] =
        if (name.matches("(?i:^[a-z][a-z ,.'-]*$)"))
          name.pure[F]
        else A.raiseError(mkError(InvalidName))
    def validatePhoneNumber(lastname: String): F[String] =
      if (lastname.matches("^[1-9]\\d{2}-\\d{3}-\\d{4}"))
        lastname.pure[F]
      else A.raiseError(mkError(InvalidPhoneNumber))


    def validateAge(age: Int): F[Int] =
        if (age >= 18 && age < 120) age.pure[F]
        else A.raiseError(mkError(InvalidAge))


    def createValidUser(firstname: String, lastname: String, age: Int): F[User] = {
        (User.apply _).curried.pure[F] <*>
          validateName(firstname) <*>
          validatePhoneNumber(lastname) <*>
          validateAge(age)
      }
    }

}


