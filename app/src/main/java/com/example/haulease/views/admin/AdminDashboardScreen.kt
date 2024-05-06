package com.example.haulease.views.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.haulease.ui.components.SimpleDataBox

@Composable
fun AdminDashboardScreen(
  navCtrl: NavHostController
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
        text = "Admin Dashboard",
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
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        SimpleDataBox(
          image = painterResource(id = R.drawable.ship),
          label = "Shipment Received",
          dataValue = 30,
          backgroundColor = Color(0xFFE5E5E5)
        )

        SimpleDataBox(
          image = painterResource(id = R.drawable.container),
          label = "Cargo Transported",
          dataValue = 324,
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        SimpleDataBox(
          image = painterResource(id = R.drawable.weight),
          label = "Weight Shipped (kg)",
          dataValue = 395,
        )

        SimpleDataBox(
          image = painterResource(id = R.drawable.assets),
          label = "Total Income (RM)",
          dataValue = 13430,
          backgroundColor = Color(0xFFE5E5E5)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        SimpleDataBox(
          image = painterResource(id = R.drawable.active_users),
          label = "Total Active Users",
          dataValue = 45,
          backgroundColor = Color(0xFFE5E5E5)
        )

        SimpleDataBox(
          image = painterResource(id = R.drawable.completed),
          label = "Total Shipment Done",
          dataValue = 243,
        )
      }

      Spacer(modifier = Modifier.padding(bottom = 60.dp))
    }
  }
}