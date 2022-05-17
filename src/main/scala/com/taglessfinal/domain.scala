package com.taglessfinal

case class Name(value:String)
case class Phone(value:String)
case class Email(value:String)
case class Age(value:Int)
case class User(name: Name, phone:Phone, email:Email, age:Age)

sealed trait UserError
case object InvalidName extends UserError
case object InvalidAge extends UserError
case object InvalidPhoneNumber extends UserError
case object InvalidEmail extends UserError
