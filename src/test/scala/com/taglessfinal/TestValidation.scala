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
  testOption
}
