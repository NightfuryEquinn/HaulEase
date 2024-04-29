package com.example.haulease.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.haulease.R

@Composable
fun LoadingScreen(loadingProgress: Float) {
  // Adjust the opacity of the logo based on loading progress
  val imageModifier = Modifier
    .size(200.dp)
    .graphicsLayer(alpha = 0 + loadingProgress)

  Column(
    modifier = Modifier
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.logo_nobg),
      contentDescription = "HaulEase_Logo",
      modifier = imageModifier
    )

    Spacer(modifier = Modifier.height(16.dp))

    LinearProgressIndicator(
      progress = { loadingProgress },
    )
  }
}
