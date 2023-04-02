package me.buddha.chatapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.buddha.chatapplication.domain.Destination
import me.buddha.chatapplication.ui.MainViewModel

@Composable
fun HomeScreen(
  navController: NavController,
  viewModel: MainViewModel,
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    LazyColumn {
      items(viewModel.usersList.size) {
        Card(
          modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(Color.Cyan)
            .clickable {
              viewModel.selectedUser = viewModel.usersList[it]
              viewModel.openChat()
              navController.navigate(Destination.Chat.route)
            }
        ) {
          Text(
            text = viewModel.usersList[it].name,
            fontSize = 20.sp,
          )
        }
      }
    }
    Button(
      onClick = { viewModel.signOut() },
      modifier = Modifier.align(Alignment.BottomCenter)
    ) {
      Text(text = "Sign Out")
    }
  }
}