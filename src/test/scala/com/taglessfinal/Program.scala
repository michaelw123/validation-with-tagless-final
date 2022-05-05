package com.taglessfinal

import validator._
import interpretor._


object Program extends App {
  def withOption = {
    implicit val userValidatorInterpreter = userValidatorOptionInterpreter
    println("Test Option")
    println(UserValidator.validate("John", "123-456-7890", 20))
    println(UserValidator.validate("John", "123-456-7890", 25 ))
    println(UserValidator.validate("John", "123-456-7890", -1 ))
    println(UserValidator.validate(".John", "123-456-789", -1))
    println
  }
  def withTry = {
    implicit val userValidatorInterpreter = userValidatorTryInterpreter
    println("Test Try")
    println(UserValidator.validate("John", "123-456-7890", 20))
    println(UserValidator.validate("John", "123-456-7890", 25 ))
    println(UserValidator.validate("John", "123-456-7890", -1 ))
    println(UserValidator.validate(".John", "123-456-789", -1))
    println
  }
  def withEither = {
    implicit val userValidatorInterpreter = userValidatorEitherInterpreter
    println("Test Either")
    println(UserValidator.validate("John", "123-456-7890", 20))
    println(UserValidator.validate("John", "123-456-7890", 25 ))
    println(UserValidator.validate("John", "123-456-7890", -1 ))
    println(UserValidator.validate(".John", "123-456-789", -1))
    println
  }
  def withValidated = {
    implicit val userValidatorInterpreter = userValidatorValidaedInterpreter
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
