package com.example.haulease.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.haulease.R
import com.example.haulease.navigations.routes.SharedRoutes
import com.example.haulease.ui.components.SimpleTextField
import com.example.haulease.viewmodels.ForgotState
import com.example.haulease.viewmodels.ForgotVM

@Composable
fun ForgotScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit,
  forgotVM: ForgotVM = viewModel()
) {
  val context = LocalContext.current

  // State variables
  val email = remember { mutableStateOf("") }
  val newPassword = remember { mutableStateOf("") }
  val confirmNewPassword = remember { mutableStateOf("") }

  // Validations
  val passwordsMatch = newPassword == confirmNewPassword
  val allFieldsNotEmpty = email.value.isNotBlank() && newPassword.value.isNotBlank() && confirmNewPassword.value.isNotBlank()
  val isPasswordValid by remember { mutableStateOf(false) }

  // Observer
  val forgotState by forgotVM.forgotState.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(30.dp)
  ) {
    Image(
      painter = painterResource(id = R.drawable.logo_nobg),
      contentDescription = "HaulEase_Logo",
      modifier = Modifier.size(100.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "Reset Your",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.squada)),
        fontSize = 48.sp
      )
    )

    Text(
      text = "Password",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.squada)),
        fontSize = 32.sp
      ),
      color = Color(0xFFFCA311)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      modifier = Modifier.clickable(
        onClick = {
          onBack()
          navCtrl.navigate(SharedRoutes.Register.routes)
        }
      ),
      text = "Register as new consignor",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.libre)),
        fontSize = 12.sp,
        textDecoration = TextDecoration.Underline
      )
    )

    Spacer(modifier = Modifier.height(32.dp))

    SimpleTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      inputText = email,
      onValueChange = { newValue ->
        email.value = newValue
      },
      label = "Email",
    )

    Spacer(modifier = Modifier.height(8.dp))

    SimpleTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      inputText = newPassword,
      onValueChange = { newValue ->
        newPassword.value = newValue
      },
      label = "New Password",
      isSensitive = true,
    )

    Spacer(modifier = Modifier.height(8.dp))

    SimpleTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      inputText = confirmNewPassword,
      onValueChange = { newValue ->
        confirmNewPassword.value = newValue
      },
      label = "Confirm New Password",
      isSensitive = true,
    )

    Spacer(modifier = Modifier.height(32.dp))

    when (forgotState) {
      is ForgotState.SUCCESS -> {
        navCtrl.navigate(SharedRoutes.Login.routes) {
          launchSingleTop = true
        }
      }
      is ForgotState.LOADING -> {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
        )
      }
      is ForgotState.INITIAL -> {
        Button(
          onClick = {
            forgotVM.resetConsignor(
              email.value,
              newPassword.value,
              confirmNewPassword.value,
              context
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = 80.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311)),
          shape = RoundedCornerShape(5.dp),
          enabled = passwordsMatch && allFieldsNotEmpty && isPasswordValid,
        ) {
          Text(
            text = "Reset",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada)),
              fontSize = 24.sp,
            )
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .clickable(
          onClick = {
            onBack()
            navCtrl.navigate(SharedRoutes.Login.routes)
          }
        ),
      text = "Back to Login",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.libre)),
        fontSize = 12.sp,
        textDecoration = TextDecoration.Underline
      )
    )
  }
}