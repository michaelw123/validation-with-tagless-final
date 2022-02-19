package com.taglessfinal

import com.taglessfinal.domain.User


trait Validation[F[_]] {
     def validateFirstName(fn:String):F[String]
     def validatelastName(ln:String):F[String]
     def validateAge(age:Int):F[Int]
}
trait Validator[F[_], A] {
  def validate(v:A) :F[A]
}
object NameValidator extends Validator[Option, String] {
  def validate(v:String) = if (v.length<3) None else Some(v)
}

object AgeValidator extends Validator[Option, Int] {
  def validate(v:Int) = if (v<18) None else Some(v)
}
object UserValidation extends Validation[Option] {
  override def validateFirstName(fn:String):Option[String] = NameValidator.validate(fn)
  override def validatelastName(ln:String):Option[String] = NameValidator.validate(ln)
  override def validateAge(age:Int):Option[Int] =  AgeValidator.validate(age)
}
object validateCandidates extends App {
  //val validation = new NameValidation[Option]
  val nameV = new Validator[Option, String]{
    def validate(v:String) = if (v.length<3) None else Some(v)
  }
  val ageV = new Validator[Option, Int]{
    def validate(v:Int) = if (v<18) None else Some(v)
  }
  def validate(firstName:String, lastName:String, age:Int) = for {
    fn <- nameV.validate(firstName)
    ln <- nameV.validate(lastName)
    age <- ageV.validate(age)
  } yield User(0, fn, ln, age)

  val x = validate("aaaa", "bbbb", 30)

  println(x)

}

