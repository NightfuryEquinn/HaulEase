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
import com.example.haulease.models.Shipment
import com.example.haulease.ui.components.SimpleEmptyBox
import com.example.haulease.ui.components.SimpleViewBox
import com.example.haulease.viewmodels.admin.AdminHistoryState
import com.example.haulease.viewmodels.admin.AdminHistoryVM
import kotlinx.coroutines.launch

@Composable
fun AdminHistoryScreen(
  navCtrl: NavHostController,
  adminHistoryVM: AdminHistoryVM = viewModel()
) {
  val context = LocalContext.current
  val cScope = rememberCoroutineScope()
  var allShipmentsHistory: List<Shipment> = adminHistoryVM.allShipmentsHistory

  // Observer
  val adminHistoryState by adminHistoryVM.adminHistoryState.collectAsState()

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
        text = "All History",
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
      when (adminHistoryState) {
        is AdminHistoryState.SUCCESS -> {
          if (allShipmentsHistory.isEmpty()) {
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
              name = "No Past History"
            )
          } else {
            allShipmentsHistory.forEach { shipment ->
              SimpleViewBox(
                navCtrl = navCtrl,
                modifier = Modifier
                  .clip(shape = RoundedCornerShape(5.dp))
                  .fillMaxSize()
                  .background(Color(0xFFE5E5E5)),
                rowModifier = Modifier
                  .fillMaxSize(),
                image = painterResource(id = R.drawable.shipment_placeholder),
                shipmentId = shipment.id,
                consignorId = shipment.consignorId,
                status = shipment.status
              )

              Spacer(modifier = Modifier.height(20.dp))
            }
          }
        }
        is AdminHistoryState.LOADING -> {
          LinearProgressIndicator(
            modifier = Modifier
              .align(Alignment.CenterHorizontally)
          )
        }
        is AdminHistoryState.INITIAL -> {
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

      Spacer(modifier = Modifier.padding(bottom = 60.dp))
    }
  }

  DisposableEffect(Unit) {
    val job = cScope.launch {
      adminHistoryVM.loadAllShipmentsHistory(context)
    }

    onDispose {
      job.cancel()
      adminHistoryVM.clearAllShipmentsHistory()
    }
  }
}