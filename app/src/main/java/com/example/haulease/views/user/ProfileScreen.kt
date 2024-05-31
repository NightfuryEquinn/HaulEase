package com.example.haulease.views.user

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
import com.example.haulease.models.Consignor
import com.example.haulease.ui.components.SimpleEmptyBox
import com.example.haulease.ui.components.SimpleLabelDesc
import com.example.haulease.viewmodels.user.ProfileState
import com.example.haulease.viewmodels.user.ProfileVM
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
  navCtrl: NavHostController,
  profileVM: ProfileVM = viewModel()
) {
  val context = LocalContext.current
  val cScope = rememberCoroutineScope()
  var theUserProfile: Consignor? = profileVM.theUserProfile

  // Observer
  val profileState by profileVM.profileState.collectAsState()

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
        text = "Profile",
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
      when (profileState) {
        is ProfileState.SUCCESS -> {
          AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
              .data(theUserProfile?.avatar)
              .crossfade(true)
              .build(),
            contentDescription = null,
            modifier = Modifier
              .clip(shape = RoundedCornerShape(5.dp))
              .fillMaxSize()
              .height(150.dp)
              .aspectRatio(1.0f),
            contentScale = ContentScale.Crop
          )

          Spacer(modifier = Modifier.height(20.dp))

          SimpleLabelDesc(
            label = "Username",
            desc = theUserProfile?.username
          )

          SimpleLabelDesc(
            label = "Email",
            desc = theUserProfile?.email
          )

          SimpleLabelDesc(
            label = "Residential Address",
            desc = theUserProfile?.address
          )

          SimpleLabelDesc(
            label = "Company Name",
            desc = if (theUserProfile?.company.isNullOrBlank()) "None" else theUserProfile?.company
          )

          SimpleLabelDesc(
            label = "Company Email",
            desc = if (theUserProfile?.companyEmail.isNullOrBlank()) "None" else theUserProfile?.companyEmail
          )

          SimpleLabelDesc(
            label = "Company Address",
            desc = if (theUserProfile?.companyAddress.isNullOrBlank()) "None" else theUserProfile?.companyAddress
          )

          Button(
            onClick = {
              profileVM.logoutConsignor()
              navCtrl.navigate("MainScreen") {
                launchSingleTop = true
              }
            },
            modifier = Modifier
              .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
            shape = RoundedCornerShape(5.dp),
          ) {
            Text(
              text = "Log Out",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.squada)),
                fontSize = 24.sp,
                color = Color(0xFFE5E5E5)
              )
            )
          }
        }
        is ProfileState.LOADING -> {
          LinearProgressIndicator(
            modifier = Modifier
              .align(Alignment.CenterHorizontally)
          )
        }
        is ProfileState.INITIAL -> {
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

    Spacer(modifier = Modifier.padding(bottom = 52.dp))
  }

  DisposableEffect(Unit) {
    val job = cScope.launch {
      theUserProfile = profileVM.loadConsignorProfile(context)
    }

    onDispose {
      job.cancel()
      profileVM.clearConsignorProfile()
    }
  }
}