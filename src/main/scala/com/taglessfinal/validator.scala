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
      def validateFirstName(name: String): F[String] =
        if (name.matches("(?i:^[a-z][a-z ,.'-]*$)"))
          name.pure[F]
        else A.raiseError(mkError(InvalidFirstName))
    def validateLastName(lastname: String): F[String] =
      if (lastname.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
        lastname.pure[F]
      else A.raiseError(mkError(InvalidLastName))


    def validateAge(age: Int): F[Int] =
        if (age >= 18 && age < 120) age.pure[F]
        else A.raiseError(mkError(InvalidAge))


    def createValidUser(firstname: String, lastname: String, age: Int): F[User] = {
        (User.apply _).curried.pure[F] <*>
          validateFirstName(firstname) <*>
          validateLastName(lastname) <*>
          validateAge(age)
      }
    }

}


