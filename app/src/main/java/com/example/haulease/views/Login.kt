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
import com.example.haulease.navigations.routes.AdminRoutes
import com.example.haulease.navigations.routes.SharedRoutes
import com.example.haulease.navigations.routes.UserRoutes
import com.example.haulease.ui.components.SimpleTextField
import com.example.haulease.viewmodels.LoginState
import com.example.haulease.viewmodels.LoginVM

@Composable
fun LoginScreen(
  navCtrl: NavHostController,
  loginVM: LoginVM = viewModel()
) {
  val context = LocalContext.current

  // State variables
  val email = remember { mutableStateOf("") }
  val password = remember { mutableStateOf("") }

  // Validations
  val allFieldsNotEmpty = email.value.isNotBlank() && password.value.isNotBlank()

  // Observer
  val loginState by loginVM.loginState.collectAsState()

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
      text = "Welcome Back",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.squada)),
        fontSize = 48.sp
      )
    )

    Text(
      text = "Consignor",
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
      isSingle = true
    )

    Spacer(modifier = Modifier.height(8.dp))

    SimpleTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      inputText = password,
      onValueChange = { newValue ->
        password.value = newValue
      },
      label = "Password",
      isSensitive = true,
      isSingle = true
    )

    Spacer(modifier = Modifier.height(32.dp))

    Image(
      painter = painterResource(id = R.drawable.freight),
      contentDescription = "Freight",
      modifier = Modifier
        .size(64.dp)
        .align(Alignment.CenterHorizontally),
    )

    Spacer(modifier = Modifier.height(8.dp))

    when (loginState) {
      is LoginState.SUCCESS -> {
        navCtrl.navigate(UserRoutes.Dashboard.routes) {
          launchSingleTop = true
        }
      }
      is LoginState.SUCCESSADMIN -> {
        navCtrl.navigate(AdminRoutes.AdminDashboard.routes) {
          launchSingleTop = true
        }
      }
      is LoginState.LOADING -> {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
        )
      }
      is LoginState.INITIAL -> {
        Button(
          onClick = {
            loginVM.loginConsignor(
              email.value,
              password.value,
              context
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = 80.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311)),
          shape = RoundedCornerShape(5.dp),
          enabled = allFieldsNotEmpty
        ) {
          Text(
            text = "Login",
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
            navCtrl.navigate(SharedRoutes.Forgot.routes)
          }
        ),
      text = "Forgot Password?",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.libre)),
        fontSize = 12.sp,
        textDecoration = TextDecoration.Underline
      )
    )
  }
}