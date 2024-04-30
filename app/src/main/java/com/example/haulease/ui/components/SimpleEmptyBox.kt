package com.example.haulease.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haulease.R

@Composable
fun SimpleEmptyBox(
  modifier: Modifier,
  colModifier: Modifier,
  image: Painter,
  name: String
) {
  Box(
    modifier = modifier
  ) {
    Column(
      modifier = colModifier,
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
          .size(36.dp)
      )

      Spacer(modifier = Modifier.height(15.dp))

      Text(
        text = name,
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 16.sp
        )
      )
    }
  }
}