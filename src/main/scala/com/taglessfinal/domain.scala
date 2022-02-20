package com.taglessfinal


final case class User(firstName: String, lastName: String, age:Int)

sealed trait UserError
case object InvalidFirstName extends UserError
case object InvalidLastName extends UserError
case object InvalidAge extends UserError


