package com.taglessfinal
import scala.util.chaining._

object Program extends App {
  val user1 = User(Name("John"), Phone("123-456-7890"), Email("a@b.com"), Age(20))
  val user2 = User(Name("John"), Phone("123-456-7890"), Email("a@b.com"),Age(25))
  val user3 = User(Name("John"),  Phone("123-456-789"), Email("a@b.com"),Age(-1) )
  val user4 = User(Name(".John"),  Phone("123-456-789"), Email("ab.com"),Age(-1))

  def withOption:Unit = {
    import com.taglessfinal.validator.userValidatorOptionInterpreter

    println("Test Option")
    user1.validate.tap(println)
    user2.validate.tap(println)
    user3.validate.tap(println)
    user4.validate.tap(println)
    println
  }
  def withTry:Unit = {
    import com.taglessfinal.validator.userValidatorTryInterpreter

    println("Test Try")
    user1.validate.tap(println)
    user2.validate.tap(println)
    user3.validate.tap(println)
    user4.validate.tap(println)
    println
  }
  def withEither:Unit = {
    import com.taglessfinal.validator.userValidatorEitherInterpreter

    println("Test Either")
    user1.validate.tap(println)
    user2.validate.tap(println)
    user3.validate.tap(println)
    user4.validate.tap(println)
    println
  }
  def withValidated:Unit = {
    import com.taglessfinal.validator.userValidatorValidatedInterpreter

    println("Test Validated")
    user1.validate.tap(println)
    user2.validate.tap(println)
    user3.validate.tap(println)
    user4.validate.tap(println)
    println
  }
  withOption
  withTry
  withEither
  withValidated

}
