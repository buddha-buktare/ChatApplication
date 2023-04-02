package me.buddha.chatapplication.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import me.buddha.chatapplication.data.Message
import me.buddha.chatapplication.data.User
import me.buddha.chatapplication.domain.Destination

class MainViewModel(
  private val navigator: NavHostController,
) : ViewModel() {

  private val auth: FirebaseAuth = FirebaseAuth.getInstance()
  private val dbRef = Firebase.database.reference

  var selectedUser by mutableStateOf<User?>(null)

  var message by mutableStateOf("")

  var senderUid by mutableStateOf(auth.currentUser?.uid)
  private var receiverUid = selectedUser?.uid

  private var receiverRoom by mutableStateOf<String?>("${senderUid}_${receiverUid}")
  var senderRoom by mutableStateOf<String?>("${receiverUid}_${senderUid}")

  val usersList = mutableStateListOf<User>()
  val messagesList = mutableStateListOf<Message>()

  private fun getUsers() {
    usersList.clear()
    dbRef.child("users").get().addOnSuccessListener {
      usersList.clear()
      for (user in it.children) {
        val user = user.getValue(User::class.java)
        if (user != null && user.uid != auth.currentUser?.uid) {
          usersList.add(user)
        }
      }
    }
    Log.d("TAG", "senderUid_____: ${auth.currentUser?.uid}")
  }

  fun signIn(
    email: String,
    password: String,
  ) {
    auth.signInWithEmailAndPassword(email, password)
      .addOnCompleteListener {
        if (it.isSuccessful) {
          senderUid = auth.currentUser?.uid
          getUsers()
          navigator.navigate(Destination.Home.route) {
            popUpTo(Destination.Home.route) { inclusive = true }
          }
        } else {
          Log.d("TAG", "signIn: ${it.exception?.message}")
        }
      }
  }

  fun signUp(
    email: String,
    username: String,
    password: String,
  ) {
    auth.createUserWithEmailAndPassword(email, password)
      .addOnCompleteListener {
        if (it.isSuccessful) {
          addUserToDatabase(username, email, auth.currentUser?.uid)
          navigator.navigate(Destination.SignIn.route) {
            popUpTo(Destination.SignIn.route) { inclusive = true }
          }
        } else {
          Log.d("TAG", "signUp: ${it.exception?.message}")
        }
      }
  }

  fun signOut() {
    auth.signOut()
    navigator.navigate(Destination.SignIn.route) {
      popUpTo(Destination.SignIn.route) { inclusive = true }
    }
  }

  fun openChat() {
    receiverUid = selectedUser?.uid

    receiverRoom = "${senderUid}_${receiverUid}"
    senderRoom = "${receiverUid}_${senderUid}"

    dbRef.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
      override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
        dbRef.child("chats").child(senderRoom!!).get().addOnSuccessListener {
          messagesList.clear()
          for (message in it.child("messages").children) {
            val message = message.getValue(Message::class.java)
            if (message != null) {
              messagesList.add(message)
            }
          }
        }
      }

      override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
      }
    })

    Log.d("TAG", "receiverUid: $receiverUid")
    Log.d("TAG", "senderUid: $senderUid")
    Log.d("TAG", "receiverRoom: $receiverRoom")
    Log.d("TAG", "senderRoom: $senderRoom")

    navigator.navigate(Destination.Chat.route) {
      popUpTo(Destination.Chat.route) { inclusive = true }
    }
  }

  fun sendMessage(message: String) {
    val message = Message(message, auth.currentUser?.uid ?: "")
    dbRef.child("chats").child(senderRoom!!).child("messages").push()
      .setValue(message).addOnSuccessListener {
        dbRef.child("chats").child(receiverRoom!!).child("messages").push()
          .setValue(message).addOnSuccessListener {
            this.message = ""
            Log.d("TAG", "sendMessage: Message sent")
          }
      }
  }

  private fun addUserToDatabase(username: String, email: String, uid: String?) {
    if (uid != null) {
      val user = User(uid, username, email)
      dbRef.child("users").child(uid).setValue(user)
    }
  }
}

class MainViewModelFactory(
  private val navController: NavHostController,
) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return MainViewModel(navController) as T
  }
}