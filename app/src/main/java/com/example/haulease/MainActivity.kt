package com.example.haulease

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import com.example.haulease.models.Sessions
import com.example.haulease.ui.theme.HaulEaseTheme
import com.example.haulease.viewmodels.LoadingVM
import com.example.haulease.views.LoadingScreen

class MainActivity : ComponentActivity() {
  // Get loading view model
  private val loadingVM by viewModels<LoadingVM>()

  // Shared preferences for sessions
  private lateinit var sharedPreferences: SharedPreferences

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HaulEaseTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          // Observe the loading progress
          val loadingProgress by loadingVM.loadingProgress.collectAsState()

          if (loadingProgress < 1.0f) {
            LoadingScreen(loadingProgress)
          } else {
            MainScreen()
          }
        }
      }
    }

    sharedPreferences = getSharedPreferences("_sessions", MODE_PRIVATE)

    // Retrieve data from shared preferences
    val sessionToken =  sharedPreferences.getString("_sessionToken", null)
    val sessionEmail = sharedPreferences.getString("_sessionEmail", null)
    val sessionRole = sharedPreferences.getString("_sessionRole", null)

    // Restore last session
    Sessions.sessionToken = sessionToken
    Sessions.sessionEmail = sessionEmail
    Sessions.sessionRole = sessionRole
  }

  override fun onStop() {
   super.onStop()

   sharedPreferences.edit {
     putString("_sessionToken", Sessions.sessionToken)
     putString("_sessionEmail", Sessions.sessionEmail)
     putString("_sessionRole", Sessions.sessionRole)
   }
  }
}
