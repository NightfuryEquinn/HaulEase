package com.example.haulease.ui.components

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.haulease.R
import com.example.haulease.models.Cargo

@Composable
fun SimpleCargoBox(
  cargo: Cargo,
  cargoCount: Int,
  onRemove: () -> Unit
) {
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
      if (cargo.image.isNotEmpty()) {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data(cargo.image)
            .crossfade(true)
            .build(),
          contentDescription = null,
          modifier = Modifier
            .clip(shape = RoundedCornerShape((2.5).dp))
            .size(75.dp),
          contentScale = ContentScale.Crop
        )
      } else {
        Image(
          painter = painterResource(id = R.drawable.image),
          contentDescription = null,
          modifier = Modifier
            .clip(shape = RoundedCornerShape((2.5).dp))
            .size(75.dp),
          contentScale = ContentScale.Crop
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Text(
          text = "Cargo $cargoCount",
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
              onRemove()
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

    Column(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      Text(
        text = "Dimensions: ${cargo.length}m x ${cargo.width}m x ${cargo.height}m",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.libre)),
          fontSize = 12.sp
        )
      )

      Spacer(modifier = Modifier.width(10.dp))

      Text(
        text = "Type: ${cargo.type}",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.libre)),
          fontSize = 12.sp
        )
      )

      Spacer(modifier = Modifier.width(10.dp))

      Text(
        text = "Description: ${cargo.description}",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.libre)),
          fontSize = 12.sp
        )
      )
    }
  }

  Spacer(modifier = Modifier.height(10.dp))
}