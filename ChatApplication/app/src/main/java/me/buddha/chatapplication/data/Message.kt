package me.buddha.chatapplication.data

class Message(
  val text: String = "",
  val senderId: String = ""
) {
  constructor() : this("", "")
}