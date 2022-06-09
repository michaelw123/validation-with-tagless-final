package com.taglessfinal

object Program extends App {
  val user1 = User(Name("John"), Phone("123-456-7890"), Email("a@b.com"), Age(20))
  val user2 = User(Name("John"), Phone("123-456-7890"), Email("a@b.com"),Age(25))
  val user3 = User(Name("John"),  Phone("123-456-789"), Email("a@b.com"),Age(-1) )
  val user4 = User(Name(".John"),  Phone("123-456-789"), Email("ab.com"),Age(-1))

  def withOption:Unit = {
    import com.taglessfinal.validator.userValidatorOptionInterpreter

    println("Test Option")
    println(user1.validate)
    println(user2.validate)
    println(user3.validate)
    println(user4.validate)
    println
  }
  def withTry:Unit = {
    import com.taglessfinal.validator.userValidatorTryInterpreter

    println("Test Try")
    println(user1.validate)
    println(user2.validate)
    println(user3.validate)
    println(user4.validate)
    println
  }
  def withEither:Unit = {
    import com.taglessfinal.validator.userValidatorEitherInterpreter

    println("Test Either")
    println(user1.validate)
    println(user2.validate)
    println(user3.validate)
    println(user4.validate)
    println
  }
  def withValidated:Unit = {
    import com.taglessfinal.validator.userValidatorValidatedInterpreter

    println("Test Validated")
    println(user1.validate)
    println(user2.validate)
    println(user3.validate)
    println(user4.validate)
    println
  }
  withOption
  withTry
  withEither
  withValidated

}
