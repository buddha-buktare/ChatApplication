package me.buddha.chatapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.buddha.chatapplication.ui.MainViewModel

@Composable
fun ChatScreen(
  navController: NavController,
  viewModel: MainViewModel,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
  ) {
    Text(
      text = viewModel.selectedUser?.name ?: "No User Selected",
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally),
      fontSize = 40.sp
    )
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      items(viewModel.messagesList.size) {
        if (viewModel.messagesList[it].senderId == viewModel.selectedUser?.uid)
          Text(
            text = viewModel.messagesList[it].text,
            modifier = Modifier
              .fillMaxWidth()
              .padding(end = 8.dp)
              .align(Alignment.End)
              .background(color = androidx.compose.ui.graphics.Color.Green)
          )
        else
          Text(
            text = viewModel.messagesList[it].text,
            modifier = Modifier
              .fillMaxWidth()
              .align(Alignment.Start)
              .padding(start = 8.dp)
              .background(color = androidx.compose.ui.graphics.Color.Yellow)
          )
      }
    }
    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      TextField(
        value = viewModel.message,
        onValueChange = { viewModel.message = it },
        label = { Text(text = "Enter Message") },
        modifier = Modifier.weight(1f),
      )
      Button(onClick = { viewModel.sendMessage(viewModel.message) }) {
        Text(text = "Send")
      }
    }
  }
}