package com.taglessfinal

object Program extends App {
  def withOption:Unit = {
    import com.taglessfinal.validator.UserValidatorOption

    println("Test Option")
    println(User(Name("John"), Phone("123-456-7890"), Email("a@b.com"), Age(20)).validate)
    println(User(Name("John"), Phone("123-456-7890"), Email("a@b.com"),Age(25) ).validate)
    println(User(Name("John"),  Phone("123-456-789"), Email("a@b.com"),Age(-1) ).validate)
    println(User(Name(".John"),  Phone("123-456-789"), Email("ab.com"),Age(-1)).validate)
    println
  }
  def withTry:Unit = {
    import com.taglessfinal.validator.UserValidatorTry

    println("Test Try")
    println(User(Name("John"), Phone("123-456-7890"), Email("a@b.com"), Age(20)).validate)
    println(User(Name("John"), Phone("123-456-7890"), Email("a@b.com"),Age(25) ).validate)
    println(User(Name("John"),  Phone("123-456-789"), Email("a@b.com"),Age(-1) ).validate)
    println(User(Name(".John"),  Phone("123-456-789"), Email("ab.com"),Age(-1)).validate)
    println
  }
  def withEither:Unit = {
    import com.taglessfinal.validator.UserValidatorEither

    println("Test Either")
    println(User(Name("John"), Phone("123-456-7890"), Email("a@b.com"), Age(20)).validate)
    println(User(Name("John"), Phone("123-456-7890"), Email("a@b.com"),Age(25) ).validate)
    println(User(Name("John"),  Phone("123-456-789"), Email("a@b.com"),Age(-1) ).validate)
    println(User(Name(".John"),  Phone("123-456-789"), Email("ab.com"),Age(-1)).validate)
    println
  }
  def withValidated:Unit = {
    import com.taglessfinal.validator.UserValidatorValidated

    println("Test Validated")
    println(User(Name("John"), Phone("123-456-7890"), Email("a@b.com"), Age(20)).validate)
    println(User(Name("John"), Phone("123-456-7890"), Email("a@b.com"),Age(25) ).validate)
    println(User(Name("John"),  Phone("123-456-789"), Email("a@b.com"),Age(-1) ).validate)
    println(User(Name(".John"),  Phone("123-456-789"), Email("ab.com"),Age(-1)).validate)
    println
  }
  withOption
  withTry
  withEither
  withValidated

}
