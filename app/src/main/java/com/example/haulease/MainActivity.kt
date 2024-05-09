package com.example.haulease

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
import com.example.haulease.ui.theme.HaulEaseTheme
import com.example.haulease.viewmodels.LoadingVM
import com.example.haulease.views.LoadingScreen

class MainActivity : ComponentActivity() {
  // Get loading view model
  private val loadingVM by viewModels<LoadingVM>()

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
  }
}
