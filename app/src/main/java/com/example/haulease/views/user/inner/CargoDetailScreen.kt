package com.example.haulease.views.user.inner

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.haulease.R
import com.example.haulease.models.Cargo
import com.example.haulease.ui.components.SimpleEmptyBox
import com.example.haulease.ui.components.SimpleLabelDesc
import com.example.haulease.viewmodels.user.inner.CargoDetailVM
import com.example.haulease.viewmodels.user.inner.CargoState
import kotlinx.coroutines.launch

@Composable
fun CargoDetailScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit,
  cargoId: Int = 0,
  shipmentId: Int,
  cargoDetailVM: CargoDetailVM = viewModel()
) {
  val context = LocalContext.current
  val cScope = rememberCoroutineScope()
  var theCargoDetail: Cargo? = null

  // Observer
  val cargoState by cargoDetailVM.cargoState.collectAsState()

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
        text = "Cargo Details",
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
      when (cargoState) {
        is CargoState.SUCCESS -> {
          if (theCargoDetail != null) {
            if (theCargoDetail!!.image.isNotEmpty()) {
              AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                  .data(theCargoDetail?.image)
                  .crossfade(true)
                  .build(),
                contentDescription = null,
                modifier = Modifier
                  .clip(shape = RoundedCornerShape(5.dp))
                  .fillMaxSize()
                  .height(200.dp)
                  .aspectRatio(1.0f),
                contentScale = ContentScale.Crop
              )
            } else {
              Image(
                painterResource(id = R.drawable.image),
                contentDescription = null,
                modifier = Modifier
                  .clip(shape = RoundedCornerShape(5.dp))
                  .fillMaxSize()
                  .height(200.dp)
                  .aspectRatio(1.0f)
              )
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          SimpleLabelDesc(
            label = "Type",
            desc = theCargoDetail?.type ?: ""
          )

          SimpleLabelDesc(
            label = "Weight (in kg)",
            desc = (theCargoDetail?.weight ?: "").toString()
          )

          SimpleLabelDesc(
            label = "Length (in m)",
            desc = (theCargoDetail?.length ?: "").toString()
          )

          SimpleLabelDesc(
            label = "Width (in m)",
            desc = (theCargoDetail?.width ?: "").toString()
          )

          SimpleLabelDesc(
            label = "Height (in m)",
            desc = (theCargoDetail?.height ?: "").toString()
          )

          SimpleLabelDesc(
            label = "Description",
            desc = theCargoDetail?.description ?: ""
          )
        }
        is CargoState.LOADING -> {
          LinearProgressIndicator(
            modifier = Modifier
              .align(Alignment.CenterHorizontally)
          )
        }
        is CargoState.INITIAL -> {
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
    }

    Spacer(modifier = Modifier.height(10.dp))

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
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA111)),
      shape = RoundedCornerShape(5.dp),
    ) {
      Text(
        text = "Back",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 24.sp,
        )
      )
    }

    Spacer(modifier = Modifier.padding(bottom = 60.dp))
  }

  DisposableEffect(Unit) {
    val job = cScope.launch {
      cargoDetailVM.loadCargoDetail(
        cargoId,
        context
      )
      theCargoDetail = cargoDetailVM.theCargoDetail
    }

    onDispose {
      job.cancel()
      cargoDetailVM.clearCargoDetail()
    }
  }
}