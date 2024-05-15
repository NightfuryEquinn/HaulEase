package com.example.haulease.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.haulease.R
import com.example.haulease.navigations.routes.AdminInnerRoutes
import com.example.haulease.navigations.routes.UserInnerRoutes
import com.example.haulease.navigations.routes.UserRoutes

@Composable
fun SimpleViewBox(
  navCtrl: NavHostController,
  modifier: Modifier,
  rowModifier: Modifier,
  image: Painter = painterResource(id = R.drawable.image),
  imageSize: Int? = 125,
  fromDatabase: Boolean = false,
  imageFromDatabase: String = "",
  shipmentId: Int? = 0,
  cargoId: Int? = 0,
  name: String? = null,
  status: String? = null,
) {
  val currentRoute = navCtrl.currentBackStackEntryAsState().value?.destination?.route

  Column(
    modifier = modifier
      .padding(12.dp)
  ) {
    Row(
      modifier = rowModifier
    ) {
      if (fromDatabase && imageFromDatabase.isNotEmpty()) {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data(imageFromDatabase)
            .crossfade(true)
            .build(),
          contentDescription = null,
          modifier = Modifier
            .clip(shape = RoundedCornerShape((2.5).dp))
            .size(imageSize!!.dp),
          contentScale = ContentScale.Crop
        )
      } else {
        Image(
          painter = image,
          contentDescription = null,
          modifier = Modifier
            .clip(shape = RoundedCornerShape((2.5).dp))
            .size(imageSize!!.dp),
          contentScale = ContentScale.Crop
        )
      }

      Spacer(modifier = Modifier.width(15.dp))

      Column(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Text(
          text = when {
            cargoId != null -> "ID: $cargoId"
            shipmentId != null -> "ID: $shipmentId"
            else -> "Name: $name"
          },
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.libre)),
            fontSize = 16.sp
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (status != null) {
          Text(
            text = status,
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          )
        }
      }
    }
    
    Spacer(modifier = Modifier.height(10.dp))

    Button(
      onClick = {
        when (currentRoute) {
          UserInnerRoutes.ShipmentDetail.routes -> {
            navCtrl.navigate("CargoDetail?cargoId=$cargoId&shipmentId=$shipmentId")
          }
          UserRoutes.Shipment.routes -> {
            navCtrl.navigate("ShipmentDetail?shipmentId=$shipmentId")
          }
          else -> {
            navCtrl.navigate(AdminInnerRoutes.AdminShipmentDetail.routes)
          }
        }
      },
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311)),
      shape = RoundedCornerShape(5.dp),
      modifier = Modifier
        .fillMaxWidth()
    ) {
      Text(
        text = "View Details",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 20.sp,
        )
      )
    }
  }
}