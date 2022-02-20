package com.taglessfinal

import cats.data.{NonEmptyList, Validated}
import scala.util.Try
import cats.implicits._
import validator._

object interpretor {
  implicit val userValidatorInterpreter = userValidatorIdInterpreter

  val userValidatorOptionInterpreter =
    userValidator[Option, Unit](_ => ())

  val userValidatorTryInterpreter =
    userValidator[Try, Throwable](err => new Throwable(err.toString))

  val userValidatorEitherInterpreter =
    userValidator[Either[UserError, *], UserError](identity)

  val userValidatorValidaedInterpreter =
    userValidator[Validated[NonEmptyList[UserError], *], NonEmptyList[UserError]](NonEmptyList(_, Nil))

}