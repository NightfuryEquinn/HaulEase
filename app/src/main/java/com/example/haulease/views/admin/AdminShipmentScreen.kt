package com.example.haulease.views.admin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.haulease.R
import com.example.haulease.navigations.TabBar
import com.example.haulease.ui.components.SimpleTabCol

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdminShipmentScreen(
  navCtrl: NavHostController
) {
  val data = listOf(
    Triple(R.drawable.image, "100001", "En-route to Harbor"),
    Triple(R.drawable.image, "100002", "En-route to Harbor"),
    Triple(R.drawable.image, "100003", "En-route to Harbor")
  )

  var selectedTabIndex by remember { mutableIntStateOf(0) }
  val pagerState = rememberPagerState {
    TabBar.TabItems.size
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
        text = "All Shipments",
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
          0 -> SimpleTabCol(navCtrl, data)
          1 -> SimpleTabCol(navCtrl, data)
          2 -> SimpleTabCol(navCtrl, data)
          3 -> SimpleTabCol(navCtrl, data)
        }
      }
    }

    Spacer(modifier = Modifier.padding(bottom = 60.dp))
  }
}