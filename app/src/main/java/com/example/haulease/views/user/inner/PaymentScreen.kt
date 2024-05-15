package com.example.haulease.views.user.inner

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.haulease.R
import com.example.haulease.models.Payment
import com.example.haulease.ui.components.SimpleEmptyBox
import com.example.haulease.ui.components.SimplePaymentBox
import com.example.haulease.viewmodels.user.inner.PaymentState
import com.example.haulease.viewmodels.user.inner.PaymentVM
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit,
  paymentId: Int = 0,
  shipmentId: Int,
  paymentVM: PaymentVM = viewModel()
) {
  val context = LocalContext.current
  val cScope = rememberCoroutineScope()
  var thePaymentDetail: Payment? = null
  var totalCargoFees: Double = 0.0

  // Observer
  val paymentState by paymentVM.paymentState.collectAsState()

  BackHandler {
    onBack()
    navCtrl.navigate("ShipmentDetail?shipmentId=$shipmentId") {
      launchSingleTop = true
    }
  }

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
          fontSize = 48.sp,
          textAlign = TextAlign.End
        )
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    when (paymentState) {
      is PaymentState.SUCCESS -> {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .weight(1f)
        ) {
          Text(
            text = if (thePaymentDetail?.first == 0.0) "Shipment First Billing" else "Shipment First Billing (Done)",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada)),
              fontSize = 24.sp,
              color = Color(0xFFFCA111)
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          SimplePaymentBox(
            originTruckTravelFees = 25.90,
            originTruckLoadingFees = 15.90,
            harborMeasurementFees = 14.90,
            harborLoadingFees = 25.90,
            onPayClick = {
              cScope.launch {
                paymentVM.makePaymentRequest(
                  paymentId,
                  1,
                  (25.90 + 15.90 + 14.90 + 25.90),
                  context
                )
              }
            },
            isPaid = thePaymentDetail?.first != 0.0
          )

          Spacer(modifier = Modifier.height(25.dp))

          Text(
            text = if (thePaymentDetail?.second == 0.0) "Shipment Second Billing" else "Shipment Second Billing (Done)",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada)),
              fontSize = 24.sp,
              color = Color(0xFFFCA111)
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          SimplePaymentBox(
            totalCargoFees = totalCargoFees,
            onPayClick = {
              cScope.launch {
                paymentVM.makePaymentRequest(
                  paymentId,
                  2,
                  totalCargoFees,
                  context
                )
              }
            },
            isPaid = thePaymentDetail?.second != 0.0
          )

          Spacer(modifier = Modifier.height(25.dp))

          Text(
            text = if (thePaymentDetail?.final == 0.0) "Shipment Third Billing" else "Shipment Third Billing (Done)",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada)),
              fontSize = 24.sp,
              color = Color(0xFFFCA111)
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          SimplePaymentBox(
            harborUnloadingFees = 15.90,
            destTruckLoadingFees = 5.90,
            destTruckTravelFees = 15.90,
            onPayClick = {
              cScope.launch {
                paymentVM.makePaymentRequest(
                  paymentId,
                  3,
                  (15.90 + 5.90 + 15.90),
                  context
                )
              }
            },
            isPaid = thePaymentDetail?.final != 0.0
          )
        }
      }
      is PaymentState.PAIDSUCCESS -> {
        onBack()
        navCtrl.navigate("ShipmentDetail?shipmentId=$shipmentId") {
          launchSingleTop = true
        }
      }
      is PaymentState.LOADING -> {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
        )
      }
      is PaymentState.INITIAL -> {
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

    Spacer(modifier = Modifier.height(30.dp))

    Button(
      onClick = {
        onBack()
        navCtrl.navigate("ShipmentDetail?shipmentId=$shipmentId") {
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

  DisposableEffect(Unit) {
    val job = cScope.launch {
      paymentVM.loadPaymentDetails(
        shipmentId,
        paymentId,
        context
      )
      thePaymentDetail = paymentVM.thePaymentDetail
      totalCargoFees = paymentVM.totalCargoFees
    }

    onDispose {
      job.cancel()
      paymentVM.clearPaymentDetail()
    }
  }
}