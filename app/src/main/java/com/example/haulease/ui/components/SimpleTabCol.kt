package com.example.haulease.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.haulease.R
import com.example.haulease.models.Sessions
import com.example.haulease.models.Shipment

@Composable
fun SimpleTabCol(
  navCtrl: NavHostController,
  datas: List<Shipment>
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp)
      .verticalScroll(rememberScrollState()),
  ) {
    datas.forEach { data ->
      val theConsignorId = if (Sessions.sessionRole == "Admin") {
        data.consignorId
      } else {
        Sessions.sessionToken?.toInt()
      }

      SimpleViewBox(
        navCtrl = navCtrl,
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .fillMaxSize()
          .background(Color(0xFFE5E5E5)),
        rowModifier = Modifier
          .fillMaxSize(),
        image = painterResource(id = R.drawable.shipment_placeholder),
        consignorId = theConsignorId,
        shipmentId = data.id,
        status = data.status
      )

      Spacer(modifier = Modifier.height(10.dp))
    }
  }
}