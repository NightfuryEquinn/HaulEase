package com.example.haulease.views.admin.inner

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.haulease.ui.components.SimpleViewBox
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.list.ListDialog
import com.maxkeppeler.sheets.list.models.ListOption
import com.maxkeppeler.sheets.list.models.ListSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminShipmentDetailScreen(
  navCtrl: NavHostController
) {
  // Option dialog for shipment status
  var displayOption by remember { mutableStateOf("") }
  val listOptionState = rememberUseCaseState(onFinishedRequest = { Log.d("Dialog", "Change Status")})

  val options = listOf(
    ListOption(titleText = "Driver on the way for cargo pickup"),
    ListOption(titleText = "Driver on the way to harbour"),
    ListOption(titleText = "Cargo arrived at harbour"),
    ListOption(titleText = "Cargo under inspection and measuring"),
    ListOption(titleText = "Cargo loaded onto deck"),
    ListOption(titleText = "Cargo has departed from harbour"),
    ListOption(titleText = "Cargo has arrived at harbour near destination"),
    ListOption(titleText = "Cargo is on the way to destination"),
    ListOption(titleText = "Cargo has arrived"),
  )

  ListDialog(
    state = listOptionState,
    selection = ListSelection.Single(
      options = options
    ) { _, option ->
      displayOption = option.titleText
    }
  )

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
        text = "Shipment Detail",
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
      Column(
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .fillMaxWidth()
          .background(Color(0xFFE5E5E5))
          .padding(12.dp),
      ) {
        Text(
          text = "Truck & Driver Details",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 20.sp,
            color = Color(0xFFFCA111)
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "ID: 100001",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.libre)),
            fontSize = 12.sp
          )
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
          text = "Driver Name: Jason Doe",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.libre)),
            fontSize = 12.sp
          )
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
          text = "License Plate: VEE 7913",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.libre)),
            fontSize = 12.sp
          )
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
          text = "Status",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 20.sp,
            color = Color(0xFFFCA111)
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Arrived at harbor near destination, waiting to unload",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.libre)),
            fontSize = 12.sp
          )
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
          text = "Origin to Destination",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 20.sp,
            color = Color(0xFFFCA111)
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "C-27-07, Parkhill Residence, MRANTI Park, 57000 Bukit Jalil, Kuala Lumpur",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.libre)),
            fontSize = 12.sp,
          )
        )

        Spacer(modifier = Modifier.height(5.dp))

        Icon(
          imageVector = Icons.Filled.ArrowDownward,
          contentDescription = null,
          tint = Color(0xFF14213D),
          modifier = Modifier
            .size(36.dp)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
          text = "138 Cecil Street #12-01 Cecil Court, Singapore 069538",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.libre)),
            fontSize = 12.sp,
          )
        )
      }

      Spacer(modifier = Modifier.height(25.dp))

      Text(
        text = "Tracking Map",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 28.sp,
        )
      )

      Spacer(modifier = Modifier.height(5.dp))

      Text(
        text = "Last updated at 01/01/2024 4:40p.m.",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.libre)),
          fontSize = 12.sp,
        )
      )

      Spacer(modifier = Modifier.height(25.dp))

      // TODO MapView

      Spacer(modifier = Modifier.height(25.dp))

      Text(
        text = "List of Cargos",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 28.sp,
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      SimpleViewBox(
        navCtrl = navCtrl,
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .fillMaxSize()
          .background(Color(0xFFE5E5E5)),
        rowModifier = Modifier
          .fillMaxSize(),
        image = painterResource(id = R.drawable.image),
        imageSize = 50,
        id = "100001",
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Button(
      onClick = {
        listOptionState.show()
      },
      modifier = Modifier
        .fillMaxWidth(),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
      shape = RoundedCornerShape(5.dp),
    ) {
      Text(
        text = "Change Status",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 24.sp,
          color = Color(0xFFE5E5E5)
        )
      )
    }

    Spacer(modifier = Modifier.padding(bottom = 52.dp))
  }
}