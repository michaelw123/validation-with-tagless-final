package com.taglessfinal


final case class User(name: String, age:Int)

sealed trait UserError
case object InvalidName extends UserError
case object InvalidAge extends UserError


final case class Contact(phone:String, email:String)
sealed trait ContactError
case object InvalidPhoneNumber extends ContactError
case object InvalidEmail extends ContactError
