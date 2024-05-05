package com.example.haulease.views.user.inner

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.haulease.navigations.routes.UserInnerRoutes
import com.example.haulease.navigations.routes.UserRoutes
import com.example.haulease.ui.components.SimpleTextField

@Composable
fun CreateShipmentScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit
) {
  // State variables
  val name = remember { mutableStateOf("") }
  val contact = remember { mutableStateOf("") }
  val origin = remember { mutableStateOf("") }
  val dest = remember { mutableStateOf("") }

  // Validations
  val allFieldsNotEmpty = name.value.isNotBlank()
      && contact.value.isNotBlank()
      && origin.value.isNotBlank()
      && dest.value.isNotBlank()

  val originOrDest = origin.value.isNotBlank() && dest.value.isNotBlank()

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
        text = "Create Shipment",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 48.sp,
          textAlign = TextAlign.End
        )
      )
    }

    Spacer(modifier = Modifier.height(25.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .weight(1f)
    ) {
      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = name,
        onValueChange = { newValue ->
          name.value = newValue
        },
        label = "Receiver Name",
        isSingle = true
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = contact,
        onValueChange = { newValue ->
          contact.value = newValue
        },
        label = "Receiver Contact",
        isSingle = true
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = origin,
        onValueChange = { newValue ->
          origin.value = newValue
        },
        label = "Origin Address",
        maxLines = 5
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = dest,
        onValueChange = { newValue ->
          dest.value = newValue
        },
        label = "Destination Address",
        maxLines = 5
      )

      Spacer(modifier = Modifier.height(8.dp))

      Button(
        onClick = {
          Log.d("Create Shipment", "Check origin or destination address")
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(end = 120.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
        shape = RoundedCornerShape(5.dp),
        enabled = originOrDest
      ) {
        Text(
          text = "Check Location",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 20.sp,
            color = Color(0xFFE5E5E5)
          )
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // TODO MapView

      Spacer(modifier = Modifier.height(25.dp))

      Text(
        text = "List of Cargos",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 28.sp,
        )
      )

      Spacer(modifier = Modifier.height(20.dp))

      // List of cargos component

      Spacer(modifier = Modifier.height(20.dp))

      Button(
        onClick = {
          navCtrl.navigate(UserInnerRoutes.CreateCargo.routes)
        },
        modifier = Modifier
          .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
        shape = RoundedCornerShape(5.dp),
      ) {
        Text(
          text = "Add New Cargo",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 20.sp,
            color = Color(0xFFE5E5E5)
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = {
          onBack()
          navCtrl.navigate(UserRoutes.Shipment.routes) {
            launchSingleTop = true
          }
        },
        modifier = Modifier
          .weight(0.35f),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
        shape = RoundedCornerShape(5.dp),
      ) {
        Text(
          text = "Place",
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
          navCtrl.navigate(UserRoutes.Shipment.routes) {
            launchSingleTop = true
          }
        },
        modifier = Modifier
          .weight(0.35f),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA111)),
        shape = RoundedCornerShape(5.dp),
      ) {
        Text(
          text = "Cancel",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 24.sp,
          )
        )
      }
    }

    Spacer(modifier = Modifier.padding(bottom = 52.dp))
  }
}