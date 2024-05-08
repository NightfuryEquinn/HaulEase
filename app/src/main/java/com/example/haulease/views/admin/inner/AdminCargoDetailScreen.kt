package com.example.haulease.views.admin.inner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.haulease.R
import com.example.haulease.navigations.routes.AdminInnerRoutes
import com.example.haulease.ui.components.SimpleLabelDesc

@Composable
fun AdminCargoDetailScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(30.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Image(
        painter = painterResource(id = R.drawable.logo_nobg),
        contentDescription = "HaulEase_Logo",
        modifier = Modifier.size(100.dp)
      )

      Text(
        text = "Cargo Detail",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 48.sp,
          textAlign = TextAlign.End
        )
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .weight(1f)
    ) {
      Image(
        painterResource(id = R.drawable.image),
        contentDescription = null,
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .fillMaxSize()
          .height(200.dp)
          .aspectRatio(1.0f)
      )

      Spacer(modifier = Modifier.height(20.dp))

      SimpleLabelDesc(
        label = "Type",
        desc = "Small"
      )

      SimpleLabelDesc(
        label = "Weight (in kg)",
        desc = "0.1"
      )

      SimpleLabelDesc(
        label = "Length (in m)",
        desc = "0.1"
      )

      SimpleLabelDesc(
        label = "Width (in m)",
        desc = "0.1"
      )

      SimpleLabelDesc(
        label = "Height (in m)",
        desc = "0.1"
      )

      SimpleLabelDesc(
        label = "Description",
        desc = "Lorem ipsum dolor sit actum this book is very good and very fragile"
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = {
          navCtrl.navigate("EditCargo?type=&weight=&length=&width=&height=")
        },
        modifier = Modifier
          .weight(0.35f),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
        shape = RoundedCornerShape(5.dp),
      ) {
        Text(
          text = "Edit",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 24.sp,
            color = Color(0xFFE5E5E5)
          )
        )
      }

      Spacer(modifier = Modifier.width(20.dp))

      Button(
        onClick = {
          onBack()
          navCtrl.navigate(AdminInnerRoutes.AdminShipmentDetail.routes) {
            launchSingleTop = true
          }
        },
        modifier = Modifier
          .weight(0.35f),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA111)),
        shape = RoundedCornerShape(5.dp),
      ) {
        Text(
          text = "Back",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 24.sp,
          )
        )
      }
    }

    Spacer(modifier = Modifier.padding(bottom = 60.dp))
  }
}