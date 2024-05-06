package com.example.haulease.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import com.example.haulease.R

@Composable
fun SimpleCargoBox(
  navCtrl: NavHostController,
  image: Painter,
  id: String? = null
) {
  // TODO Get cargo details by on id

  Column(
    modifier = Modifier
      .clip(shape = RoundedCornerShape(5.dp))
      .fillMaxSize()
      .background(Color(0xFFE5E5E5)),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
    ) {
      Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
          .clip(shape = RoundedCornerShape((2.5).dp))
          .size(75.dp),
        contentScale = ContentScale.Crop
      )

      Spacer(modifier = Modifier.width(10.dp))

      Column(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Text(
          text = "ID: $id",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.libre)),
            fontSize = 16.sp
          )
        )

        Spacer(modifier = Modifier.height(25.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Button(
            onClick = {
              navCtrl.navigate("CreateCargo?type=&weight=&length=&width=&height=&desc=&image=")
            },
            modifier = Modifier
              .height(32.dp)
              .weight(0.35f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA111)),
            shape = RoundedCornerShape(5.dp),
          ) {
            Text(
              text = "Edit",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.squada)),
                fontSize = 16.sp,
              )
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Button(
            onClick = {
              Log.d("Cargo", "Remove Cargo")
            },
            modifier = Modifier
              .height(32.dp)
              .weight(0.35f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
            shape = RoundedCornerShape(5.dp),
          ) {
            Text(
              text = "Remove",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.squada)),
                fontSize = 16.sp,
                color = Color(0xFFE5E5E5)
              )
            )
          }
        }
      }
    }
  }

  Spacer(modifier = Modifier.height(10.dp))
}