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
import com.example.haulease.models.Shipment
import com.example.haulease.models.ShipmentPayment
import com.example.haulease.ui.components.SimpleDataBox
import com.example.haulease.ui.components.SimpleEmptyBox
import com.example.haulease.ui.components.SimpleViewBox
import com.example.haulease.viewmodels.user.DashboardState
import com.example.haulease.viewmodels.user.DashboardVM
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
  navCtrl: NavHostController,
  dashboardVM: DashboardVM = viewModel()
) {
  val context = LocalContext.current
  val cScope = rememberCoroutineScope()
  var latestShipment: Shipment? = null
  var latestUnpaidShipment: ShipmentPayment? = null
  var totalShipments: Int = 0;
  var totalCargo: Int = 0;
  var totalWeight: Double = 0.0;
  var totalSpend: Double = 0.0;

  // Observer
  val dashboardState by dashboardVM.dashboardState.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(30.dp)
  ) {
    when (dashboardState) {
      is DashboardState.SUCCESS -> {
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
            text = "Dashboard",
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
          Text(
            text = "Latest Shipment",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada)),
              fontSize = 20.sp,
              color = Color(0xFFFCA111)
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          if (latestShipment != null) {
            SimpleViewBox(
              navCtrl = navCtrl,
              modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .fillMaxSize()
                .background(Color(0xFFE5E5E5)),
              rowModifier = Modifier
                .fillMaxSize(),
              image = painterResource(id = R.drawable.shipment_placeholder),
              shipmentId = latestShipment?.id,
              status = latestShipment?.status
            )
          } else {
            SimpleEmptyBox(
              modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .height(150.dp)
                .fillMaxSize()
                .background(Color(0xFFE5E5E5)),
              colModifier = Modifier
                .fillMaxSize(),
              image = painterResource(id = R.drawable.close),
              name = "No Shipment Active"
            )
          }

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

          if (latestUnpaidShipment != null) {
            SimpleViewBox(
              navCtrl = navCtrl,
              modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .fillMaxSize()
                .background(Color(0xFFE5E5E5)),
              rowModifier = Modifier
                .fillMaxSize(),
              image = painterResource(id = R.drawable.shipment_placeholder),
              shipmentId = latestUnpaidShipment?.shipment?.id,
              status = latestUnpaidShipment?.shipment?.status
            )
          } else {
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
          }

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
              dataValue = totalShipments,
              backgroundColor = Color(0xFFE5E5E5)
            )

            SimpleDataBox(
              image = painterResource(id = R.drawable.container),
              label = "Total Cargo Shipped",
              dataValue = totalCargo,
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
              dataValue = totalWeight,
            )

            SimpleDataBox(
              image = painterResource(id = R.drawable.assets),
              label = "Total Spent (RM)",
              dataValue = totalSpend,
              backgroundColor = Color(0xFFE5E5E5)
            )
          }

          Spacer(modifier = Modifier.padding(bottom = 60.dp))
        }
      }
      is DashboardState.LOADING -> {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
        )
      }
      is DashboardState.INITIAL -> {
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

        Spacer(modifier = Modifier.padding(bottom = 60.dp))
      }
    }
  }

  DisposableEffect(Unit) {
    val job = cScope.launch {
      dashboardVM.getAnalysis(context)
      latestShipment = dashboardVM.latestShipment
      latestUnpaidShipment = dashboardVM.latestUnpaidShipment
      totalShipments = dashboardVM.totalShipments
      totalCargo = dashboardVM.totalCargo
      totalWeight = dashboardVM.totalWeight
      totalSpend = dashboardVM.totalSpend
    }

    onDispose {
      job.cancel()
      dashboardVM.clearShipments()
    }
  }
}