package me.buddha.chatapplication.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.buddha.chatapplication.domain.Destination
import me.buddha.chatapplication.ui.MainViewModel

@Composable
fun SignInScreen(
  navController: NavController,
  viewModel: MainViewModel,
) {
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }

  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      TextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(text = "Email") }
      )
      Spacer(modifier = Modifier.height(8.dp))
      TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Password") }
      )
      Spacer(modifier = Modifier.height(8.dp))
      Button(onClick = { viewModel.signIn(email, password) }) {
        Text(text = "Sign In")
      }

      Spacer(modifier = Modifier.height(20.dp))
      Row(
        horizontalArrangement = Arrangement.Center
      ) {
        Text(text = "Don't have an account?")
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Sign Up",
          modifier = Modifier
            .clickable {
              navController.navigate(Destination.SignUp.route)
            },
          color = Color.Blue
        )

      }
    }
  }
}