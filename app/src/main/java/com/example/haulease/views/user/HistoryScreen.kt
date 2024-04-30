package com.example.haulease.views.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.haulease.R
import com.example.haulease.ui.components.SimpleViewBox

@Composable
fun HistoryScreen(
  navCtrl: NavHostController
) {
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
      text = "My History",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.squada)),
        fontSize = 48.sp
      )
    )

    Spacer(modifier = Modifier.height(16.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .weight(1f)
    ) {
      SimpleViewBox(
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .fillMaxSize()
          .background(Color(0xFFE5E5E5)),
        rowModifier = Modifier
          .fillMaxSize(),
        image = painterResource(id = R.drawable.image),
        id = "100001",
        status = "Completed at 01-01-2024"
      )

      Spacer(modifier = Modifier.height(20.dp))

      SimpleViewBox(
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .fillMaxSize()
          .background(Color(0xFFE5E5E5)),
        rowModifier = Modifier
          .fillMaxSize(),
        image = painterResource(id = R.drawable.image),
        id = "100001",
        status = "Completed at 01-01-2024"
      )

      Spacer(modifier = Modifier.padding(bottom = 60.dp))
    }
  }
}