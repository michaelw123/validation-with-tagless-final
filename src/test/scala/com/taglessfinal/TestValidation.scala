package com.taglessfinal

import validator._
import interpretor._


object TestValidation extends App {
  def testOption = {
    implicit val userValidatorInterpreter = userValidatorOptionInterpreter
    println("Test Option")
    println(UserValidator.validate("John", "Doe", 20))
    println(UserValidator.validate("John", "Doe", 25 ))
    println(UserValidator.validate("John", "Doe", -1 ))
    println(UserValidator.validate(".John", "Doe", -1))
    println
  }
  def testTry = {
    implicit val userValidatorInterpreter = userValidatorTryInterpreter
    println(UserValidator.validate("John", "Doe", 20))
    println(UserValidator.validate("John", "Doe", 25 ))
    println(UserValidator.validate("John", "Doe", -1 ))
    println(UserValidator.validate(".John", "Doe", -1))
    println
  }
  def testEither = {
    implicit val userValidatorInterpreter = userValidatorEitherInterpreter
    println(UserValidator.validate("John", "Doe", 20))
    println(UserValidator.validate("John", "Doe", 25 ))
    println(UserValidator.validate("John", "Doe", -1 ))
    println(UserValidator.validate(".John", "Doe", -1))
    println
  }
  def testValidated = {
    implicit val userValidatorInterpreter = userValidatorValidaedInterpreter
    println(UserValidator.validate("John", "Doe", 20))
    println(UserValidator.validate("John", "Doe", 25 ))
    println(UserValidator.validate("John", "Doe.", -1 ))
    println(UserValidator.validate(".John", "Doe", -1))
    println
  }
  testOption
  testTry
  testEither
  testValidated
}
