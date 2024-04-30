package com.example.haulease.views.user

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.compose.md_theme_light_onSecondaryContainer
import com.example.haulease.R
import com.example.haulease.navigations.TabItemBar

@Composable
fun ShipmentScreen(
  navCtrl: NavHostController
) {
  var selectedTabIndex by remember { mutableIntStateOf(0) }
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(30.dp)
  ) {
    Image(
      painter = painterResource(id = R.drawable.logo_nobg),
      contentDescription = "HaulEase_Logo",
      modifier = Modifier.size(100.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "My Shipment",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.squada)),
        fontSize = 48.sp
      )
    )

    Spacer(modifier = Modifier.height(16.dp))

    ScrollableTabRow(
      selectedTabIndex = selectedTabIndex,
      modifier = Modifier
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth(),
      edgePadding = 0.dp,
    ) {
      TabItemBar.TabItems.forEachIndexed { index, tabItem ->
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

    Button(
      onClick = {
        Log.d("Shipment", "Create Shipment")
      },
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5E5E5)),
      shape = RoundedCornerShape(5.dp),
    ) {
      Text(
        text = "Create Shipment",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 24.sp,
          color = md_theme_light_onSecondaryContainer
        )
      )
    }
  }
}