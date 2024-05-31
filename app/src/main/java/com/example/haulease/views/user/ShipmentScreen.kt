package com.example.haulease.views.user

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.haulease.navigations.TabBar
import com.example.haulease.navigations.routes.UserInnerRoutes
import com.example.haulease.ui.components.SimpleEmptyBox
import com.example.haulease.ui.components.SimpleTabCol
import com.example.haulease.viewmodels.user.ShipmentState
import com.example.haulease.viewmodels.user.ShipmentVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShipmentScreen(
  navCtrl: NavHostController,
  shipmentVM: ShipmentVM = viewModel()
) {
  val context = LocalContext.current
  val cScope = rememberCoroutineScope()
  val pickupShipment: List<Shipment> = shipmentVM.pickupShipment
  val harbourShipment: List<Shipment> = shipmentVM.harbourShipment
  val enrouteShipment: List<Shipment> = shipmentVM.enrouteShipment
  val arrivedShipment: List<Shipment> = shipmentVM.arrivedShipment

  var selectedTabIndex by remember { mutableIntStateOf(0) }
  val pagerState = rememberPagerState {
    TabBar.TabItems.size
  }

  // Observer
  val shipmentState by shipmentVM.shipmentState.collectAsState()

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
        text = "Shipment",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 48.sp,
          textAlign = TextAlign.End
        )
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    when (shipmentState) {
      is ShipmentState.SUCCESS -> {
        ScrollableTabRow(
          selectedTabIndex = selectedTabIndex,
          modifier = Modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .fillMaxWidth(),
          edgePadding = 0.dp,

          ) {
          TabBar.TabItems.forEachIndexed { index, tabItem ->
            Tab(
              selected = index == selectedTabIndex,
              onClick = {
                selectedTabIndex = index
              },
              text = {
                Text(
                  text = tabItem.title,
                  style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.squada)),
                    fontSize = 20.sp
                  )
                )
              }
            )
          }
        }

        HorizontalPager(
          state = pagerState,
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
          when (selectedTabIndex) {
            0 -> if (pickupShipment.isNotEmpty()) {
              SimpleTabCol(navCtrl, pickupShipment)
            } else {
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
                name = "No Information Found"
              )
            }
            1 -> if (harbourShipment.isNotEmpty()) {
              SimpleTabCol(navCtrl, harbourShipment)
            } else {
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
                name = "No Information Found"
              )
            }
            2 -> if (enrouteShipment.isNotEmpty()) {
              SimpleTabCol(navCtrl, enrouteShipment)
            } else {
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
                name = "No Information Found"
              )
            }
            3 -> if (arrivedShipment.isNotEmpty()) {
              SimpleTabCol(navCtrl, arrivedShipment)
            } else {
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
                name = "No Information Found"
              )
            }
          }
        }

        Button(
          onClick = {
            navCtrl.navigate(UserInnerRoutes.CreateShipment.routes)
          },
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
          shape = RoundedCornerShape(5.dp),
        ) {
          Text(
            text = "Create Shipment",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada)),
              fontSize = 24.sp,
              color = Color(0xFFE5E5E5)
            )
          )
        }
      }
      is ShipmentState.LOADING -> {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
        )
      }
      is ShipmentState.INITIAL -> {
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

        Spacer(modifier = Modifier.height(10.dp))
      }
    }

    Spacer(modifier = Modifier.padding(bottom = 52.dp))
  }

  // Link tab index to pager
  LaunchedEffect(selectedTabIndex) {
    pagerState.animateScrollToPage(selectedTabIndex)
  }

  // Link pager to tab index with direct selection
  LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
    if (!pagerState.isScrollInProgress) {
      selectedTabIndex = pagerState.currentPage
    }
  }

  DisposableEffect(Unit) {
    val job = cScope.launch {
      shipmentVM.loadShipments(context)
    }

    onDispose {
      job.cancel()
      shipmentVM.clearShipments()
    }
  }
}