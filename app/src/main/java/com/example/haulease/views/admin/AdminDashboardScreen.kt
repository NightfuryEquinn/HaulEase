package com.example.haulease.views.admin

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
import com.example.haulease.ui.components.SimpleDataBox
import com.example.haulease.ui.components.SimpleEmptyBox
import com.example.haulease.viewmodels.admin.AdminDashboardState
import com.example.haulease.viewmodels.admin.AdminDashboardVM
import kotlinx.coroutines.launch

@Composable
fun AdminDashboardScreen(
  navCtrl: NavHostController,
  adminDashboardVM: AdminDashboardVM = viewModel()
) {
  val context = LocalContext.current
  val cScope = rememberCoroutineScope()
  val shipmentsReceived: Int = adminDashboardVM.shipmentsReceived
  val cargosTransported: Int = adminDashboardVM.cargosTransported
  val weightShipped: Double = adminDashboardVM.weightShipped
  val totalIncome: Double = adminDashboardVM.totalIncome
  val totalActiveUsers: Int = adminDashboardVM.totalActiveUsers - 1
  val totalShipmentsDone: Int = adminDashboardVM.totalShipmentsDone

  // Observer
  val adminDashboardState by adminDashboardVM.adminDashboardState.collectAsState()

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

    when (adminDashboardState) {
      is AdminDashboardState.SUCCESS -> {
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
              dataValue = shipmentsReceived,
              backgroundColor = Color(0xFFE5E5E5)
            )

            SimpleDataBox(
              image = painterResource(id = R.drawable.container),
              label = "Cargo Transported",
              dataValue = cargosTransported,
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
              dataValue = weightShipped,
            )

            SimpleDataBox(
              image = painterResource(id = R.drawable.assets),
              label = "Total Income (RM)",
              dataValue = totalIncome,
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
              dataValue = totalActiveUsers,
              backgroundColor = Color(0xFFE5E5E5)
            )

            SimpleDataBox(
              image = painterResource(id = R.drawable.completed),
              label = "Total Shipment Done",
              dataValue = totalShipmentsDone,
            )
          }

          Spacer(modifier = Modifier.padding(bottom = 60.dp))
        }
      }
      is AdminDashboardState.LOADING -> {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
        )
      }
      is AdminDashboardState.INITIAL -> {
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
      adminDashboardVM.getAnalysis(context)
    }

    onDispose {
      job.cancel()
    }
  }
}