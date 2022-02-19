package com.taglessfinal

object domain {
  final case class User(id:Int,
                         firstName: String,
                         lastName: String,
                         age:Int
                       )
  trait UserRepository[F[_]] {
    def getUser(id: Int): F[Option[User]]
    def addUser(user: User): F[Unit]
    def updateUser(user: User): F[Unit]
  }

  trait Valitation[F[_]] {
    def validateFirstName(fn:String):F[String]
    def validatelastName(ln:String):F[String]
    def validateAge(age:Int):F[Int]
  }
  trait NameValidator[F[_]] {
    def validate(name:String):F[String]
  }
}
