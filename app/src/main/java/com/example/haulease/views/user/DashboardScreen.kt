package com.example.haulease.views.user

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.haulease.R
import com.example.haulease.ui.components.SimpleDataBox
import com.example.haulease.ui.components.SimpleEmptyBox
import com.example.haulease.ui.components.SimpleViewBox

@Composable
fun DashboardScreen(
  navCtrl: NavHostController
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(30.dp)
  ) {
    Image(
      painter = painterResource(id = R.drawable.logo_nobg),
      contentDescription = "HaulEase_Logo",
      modifier = Modifier.size(100.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "Dashboard",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.squada)),
        fontSize = 48.sp
      )
    )

    Spacer(modifier = Modifier.height(16.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .weight(1f)
    ) {
      Text(
        text = "Latest Shipment",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 20.sp,
          color = Color(0xFFFCA111)
        )
      )
      
      Spacer(modifier = Modifier.height(10.dp))

      SimpleViewBox(
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .fillMaxSize()
          .background(Color(0xFFE5E5E5)),
        rowModifier = Modifier
          .fillMaxSize(),
        image = painterResource(id = R.drawable.image),
        id = "100001",
        status = "En-route to Harbor"
      )

      Spacer(modifier = Modifier.height(30.dp))

      Text(
        text = "Pending Payment",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 20.sp,
          color = Color(0xFFFCA111)
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      SimpleEmptyBox(
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .height(150.dp)
          .fillMaxSize()
          .background(Color(0xFFE5E5E5)),
        colModifier = Modifier
          .fillMaxSize(),
        image = painterResource(id = R.drawable.close),
        name = "No Pending Payment"
      )

      Spacer(modifier = Modifier.height(30.dp))

      Text(
        text = "Your Data Analytics",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 20.sp,
          color = Color(0xFFFCA111)
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        SimpleDataBox(
          image = painterResource(id = R.drawable.ship),
          label = "Total Shipment Made",
          dataValue = 3.0,
          backgroundColor = Color(0xFFE5E5E5)
        )

        SimpleDataBox(
          image = painterResource(id = R.drawable.container),
          label = "Total Cargo Shipped",
          dataValue = 24,
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
          label = "Total Spent (RM)",
          dataValue = 13430,
          backgroundColor = Color(0xFFE5E5E5)
        )
      }

      Spacer(modifier = Modifier.padding(bottom = 60.dp))
    }
  }
}