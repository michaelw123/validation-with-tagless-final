package com.taglessfinal

object Program extends App {
  def withOption:Unit = {
    import com.taglessfinal.validator.UserValidatorOption

    println("Test Option")
    println(User("John", "123-456-7890", "a@b.com", 20).validate)
    println(User("John", "123-456-7890", "a@b.com",25 ).validate)
    println(User("John",  "123-456-789", "a@b.com",-1 ).validate)
    println(User(".John",  "123-456-789", "ab.com",-1).validate)
    println
  }
  def withTry:Unit = {
    import com.taglessfinal.validator.UserValidatorTry

    println("Test Try")
    println(User("John", "123-456-7890", "a@b.com", 20).validate)
    println(User("John", "123-456-7890", "a@b.com",25 ).validate)
    println(User("John",  "123-456-789", "a@b.com",-1 ).validate)
    println(User(".John",  "123-456-789", "ab.com",-1).validate)
    println
  }
  def withEither:Unit = {
    import com.taglessfinal.validator.UserValidatorEither

    println("Test Either")
    println(User("John", "123-456-7890", "a@b.com", 20).validate)
    println(User("John", "123-456-7890", "a@b.com",25 ).validate)
    println(User("John",  "123-456-789", "a@b.com",-1 ).validate)
    println(User(".John",  "123-456-789", "ab.com",-1).validate)
    println
  }
  def withValidated:Unit = {
    import com.taglessfinal.validator.UserValidatorValidated

    println("Test Validated")
    println(User("John", "123-456-7890", "a@b.com", 20).validate)
    println(User("John", "123-456-7890", "a@b.com",25 ).validate)
    println(User("John",  "123-456-789", "a@b.com",-1 ).validate)
    println(User(".John",  "123-456-789", "ab.com",-1).validate)
    println
  }
  withOption
  withTry
  withEither
  withValidated

}
