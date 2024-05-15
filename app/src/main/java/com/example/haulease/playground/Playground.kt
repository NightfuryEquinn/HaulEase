package com.example.haulease.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.haulease.R
import com.example.haulease.ui.components.SimpleEmptyBox

@Composable
fun Playground() {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(30.dp)
  ) {
    SimpleEmptyBox(
      modifier = Modifier
        .clip(shape = RoundedCornerShape(5.dp))
        .height(150.dp)
        .fillMaxSize()
        .background(Color(0xFFE5E5E5))
        .weight(1f),
      colModifier = Modifier
        .fillMaxSize(),
      image = painterResource(id = R.drawable.close),
      name = "Unable to Load Information"
    )
  }
}