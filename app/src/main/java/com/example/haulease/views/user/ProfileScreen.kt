package com.example.haulease.views.user

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.haulease.ui.components.SimpleLabelDesc

@Composable
fun ProfileScreen(
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
      text = "My Profile",
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
      Image(
        painterResource(id = R.drawable.avatar),
        contentDescription = null,
        modifier = Modifier
          .clip(shape = RoundedCornerShape(5.dp))
          .fillMaxSize()
          .height(150.dp)
          .aspectRatio(1.0f)
      )

      Spacer(modifier = Modifier.height(20.dp))

      SimpleLabelDesc(
        label = "Username",
        desc = "John Doe"
      )

      SimpleLabelDesc(
        label = "Email",
        desc = "john.doe.2024@gmail.com"
      )

      SimpleLabelDesc(
        label = "Residential Address",
        desc = "C-27-07, Parkhill Residence, MRANTI Park, Bukit Jalil, 57000 Kuala Lumpur, Kuala Lumpur"
      )

      SimpleLabelDesc(
        label = "Company Name",
        desc = "none"
      )

      SimpleLabelDesc(
        label = "Company Email",
        desc = "none"
      )

      SimpleLabelDesc(
        label = "Company Address",
        desc = "none"
      )

      Row(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Button(
          onClick = {
            Log.d("Profile", "Edit Profile")
          },
          modifier = Modifier
            .fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311)),
          shape = RoundedCornerShape(5.dp),
        ) {
          Text(
            text = "Edit Profile",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada)),
              fontSize = 24.sp,
            )
          )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Button(
          onClick = {
            Log.d("Profile", "Log Out")
          },
          modifier = Modifier
            .fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311)),
          shape = RoundedCornerShape(5.dp),
        ) {
          Text(
            text = "Log Out",
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
}