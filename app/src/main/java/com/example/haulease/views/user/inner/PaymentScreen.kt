package com.example.haulease.views.user.inner

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.haulease.R
import com.example.haulease.navigations.routes.UserInnerRoutes
import com.example.haulease.ui.components.SimplePaymentBox

@Composable
fun PaymentScreen(
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
        text = "Payment",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 48.sp
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
      Text(
        text = "Shipment First Billing",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 24.sp,
          color = Color(0xFFFCA111)
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      SimplePaymentBox(
        originTruckTravelFees = 25.0,
        originTruckLoadingFees = 152.12,
        harborMeasurementFees = 31.1,
        harborLoadingFees = 53.2
      )

      Spacer(modifier = Modifier.height(25.dp))

      Text(
        text = "Shipment Second Billing",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 24.sp,
          color = Color(0xFFFCA111)
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      SimplePaymentBox(
        totalCargoFees = 93.33
      )

      Spacer(modifier = Modifier.height(25.dp))

      Text(
        text = "Shipment Third Billing",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 24.sp,
          color = Color(0xFFFCA111)
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      SimplePaymentBox(
        harborUnloadingFees = 45.0,
        destTruckLoadingFees = 52.95,
        destTruckTravelFees = 24.52
      )
    }

    Spacer(modifier = Modifier.height(30.dp))

    Button(
      onClick = {
        onBack()
        navCtrl.navigate(UserInnerRoutes.ShipmentDetail.routes) {
          launchSingleTop = true
        }
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = 160.dp),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
      shape = RoundedCornerShape(5.dp)
    ) {
      Text(
        text = "Back",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 24.sp,
          color = Color(0xFFE5E5E5)
        )
      )
    }

    Spacer(modifier = Modifier.padding(bottom = 60.dp))
  }
}