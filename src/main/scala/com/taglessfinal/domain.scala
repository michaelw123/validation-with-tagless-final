package com.taglessfinal


final case class User(name: String, phone:String, email:String, age:Int)

sealed trait UserError
case object InvalidName extends UserError
case object InvalidAge extends UserError
case object InvalidPhoneNumber extends UserError
case object InvalidEmail extends UserError
