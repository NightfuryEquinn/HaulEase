package com.example.haulease.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTabCol(
  data: List<Triple<Int, String, String>>
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp)
      .verticalScroll(rememberScrollState()),
  ) {
    data.forEachIndexed { _, (imageId, id, status) ->
      SimpleViewBox(
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .fillMaxSize()
          .background(Color(0xFFE5E5E5)),
        rowModifier = Modifier
          .fillMaxSize(),
        image = painterResource(id = imageId),
        id = id,
        status = status
      )

      Spacer(modifier = Modifier.height(10.dp))
    }
  }
}