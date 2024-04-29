package com.example.haulease.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haulease.R

@Composable
fun SimpleViewBox(
  modifier: Modifier,
  rowModifier: Modifier,
  image: Painter,
  id: String? = null,
  name: String? = null,
  status: String? = null,
) {
  Box(
    modifier = modifier
  ) {
    Row(
      modifier = rowModifier,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Image(
        painter = image,
        contentDescription = "Placeholder",
        modifier = Modifier
          .clip(shape = RoundedCornerShape((2.5).dp))
          .size(125.dp),
        contentScale = ContentScale.Crop
      )

      Spacer(modifier = Modifier.width(15.dp))

      Column(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Column() {
          Text(
            text = if (id != null) "ID: $id" else "Name: $name",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 16.sp
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          if (status != null) {
            Text(
              text = status,
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.libre)),
                fontSize = 12.sp
              )
            )
          }
        }

        // Custom spacing depends on status is null or not
        if (status != null) {
          Spacer(modifier = Modifier.height(40.dp))
        } else {
          Spacer(modifier = Modifier.height(52.dp))
        }

        Button(
          onClick = {
            Log.d("Simple Box", "View Details")
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311)),
          shape = RoundedCornerShape(5.dp),
          modifier = Modifier
            .fillMaxWidth()
        ) {
          Text(
            text = "View Details",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada)),
              fontSize = 20.sp,
            )
          )
        }
      }
    }
  }
}