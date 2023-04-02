package me.buddha.chatapplication.data

data class User(
  val uid: String,
  val name: String,
  val email: String,
) {
  constructor() : this("", "", "")
}