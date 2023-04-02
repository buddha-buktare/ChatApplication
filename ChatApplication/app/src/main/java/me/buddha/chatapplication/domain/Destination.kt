package me.buddha.chatapplication.domain

sealed class Destination(val route: String) {
  object Chat : Destination(route = "Chat_Screen")
  object Home : Destination(route = "Home_Screen")
  object SignIn : Destination(route = "Sign_In_Screen")
  object SignUp : Destination(route = "Sign_Up_Screen")
}