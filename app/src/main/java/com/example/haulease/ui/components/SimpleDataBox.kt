package com.example.haulease.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haulease.R

@Composable
fun SimpleDataBox(
  image: Painter,
  label: String,
  dataValue: Number,
  backgroundColor: Color? = Color.Unspecified
) {
  Box(
    modifier = Modifier
      .clip(shape = RoundedCornerShape(5.dp))
      .size(145.dp)
      .background(backgroundColor!!)
  ) {
    Column(
      modifier = Modifier
        .padding(15.dp)
        .fillMaxSize(),
    ) {
      Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
          .size(64.dp)
      )

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = label,
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 16.sp
        )
      )

      Text(
        text = dataValue.toString(),
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 32.sp,
          color = Color(0xFFFCA111)
        )
      )
    }
  }
}