package com.taglessfinal

import cats.data.{NonEmptyList, Validated}
import scala.util.Try
import cats.implicits._
import validator._

object interpretor {
  implicit val userValidatorInterpreter = userValidatorIdInterpreter

  val userValidatorOptionInterpreter =
    userValidator[Option, Unit](_ => ())
//      {
//       def createValidUser(name: String, phone: String, age: Int): Option[User]
//       = {
//        for {
//          name <- validateName(name)
//          phone <- validatePhoneNumber(phone)
//          age <- validateAge(age)
//        } yield (User(name, phone, age))
//      }
//    }

  val userValidatorTryInterpreter =
    userValidator[Try, Throwable](err => new Throwable(err.toString))

  val userValidatorEitherInterpreter =
    userValidator[Either[UserError, *], UserError](identity)

  val userValidatorValidaedInterpreter =
    userValidator[Validated[NonEmptyList[UserError], *], NonEmptyList[UserError]](NonEmptyList(_, Nil))

}