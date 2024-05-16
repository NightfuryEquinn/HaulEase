package com.example.haulease.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haulease.R

@Composable
fun SimpleLabelDesc(
  label: String,
  desc: String?
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
  ) {
    Text(
      text = label,
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.squada)),
        fontSize = 20.sp,
        color = Color(0xFFFCA111)
      )
    )

    Spacer(modifier = Modifier.height(5.dp))

    Text(
      text = desc ?: "",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.libre)),
        fontSize = 12.sp,
      )
    )

    Spacer(modifier = Modifier.height(10.dp))
  }
}