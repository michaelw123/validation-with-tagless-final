package com.taglessfinal

import cats.data.{NonEmptyList, Validated}
import validator._
import interpretor._

import scala.util.Try
//import cats._
import cats.implicits._

object Program extends App {
  def withOption = {
    implicit val userValidatorInterpreter = userValidator[Option, Unit](_ => ())
      //userValidatorOptionInterpreter
    println("Test Option")
    println(UserValidator.validate("John", "123-456-7890", 20))
    println(UserValidator.validate("John", "123-456-7890", 25 ))
    println(UserValidator.validate("John", "123-456-7890", -1 ))
    println(UserValidator.validate(".John", "123-456-789", -1))
    println
  }
  def withTry = {
    implicit val userValidatorInterpreter = userValidator[Try, Throwable](err => new Throwable(err.toString))
      //userValidatorTryInterpreter
    println("Test Try")
    println(UserValidator.validate("John", "123-456-7890", 20))
    println(UserValidator.validate("John", "123-456-7890", 25 ))
    println(UserValidator.validate("John", "123-456-7890", -1 ))
    println(UserValidator.validate(".John", "123-456-789", -1))
    println
  }
  def withEither = {
    implicit val userValidatorInterpreter =  userValidator[Either[UserError, *], UserError](identity)
      //userValidatorEitherInterpreter
    println("Test Either")
    println(UserValidator.validate("John", "123-456-7890", 20))
    println(UserValidator.validate("John", "123-456-7890", 25 ))
    println(UserValidator.validate("John", "123-456-7890", -1 ))
    println(UserValidator.validate(".John", "123-456-789", -1))
    println
  }
  def withValidated = {
    implicit val userValidatorInterpreter = userValidator[Validated[NonEmptyList[UserError], *], NonEmptyList[UserError]](NonEmptyList(_, Nil))
      //userValidatorValidaedInterpreter
    println("Test Validated")
    println(UserValidator.validate("John", "123-456-7890", 20))
    println(UserValidator.validate("John", "123-456-7890", 25 ))
    println(UserValidator.validate("John", "123-456-7890.", -1 ))
    println(UserValidator.validate(".John", "123-456-789", -1))
    println
  }

  withOption
  withTry
  withEither
  withValidated

}
