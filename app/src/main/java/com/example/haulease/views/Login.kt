package com.example.haulease.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.example.haulease.R
import com.example.haulease.navigations.SharedRoutes
import com.example.haulease.ui.components.SimpleTextField

@Composable
fun LoginScreen(navCtrl: NavHostController) {
  // State variables
  val username = remember { mutableStateOf("") }
  val password = remember { mutableStateOf("") }

  // Validations
  val allFieldsNotEmpty = username.value.isNotBlank() && password.value.isNotBlank()

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
      inputText = username,
      onValueChange = { newValue ->
        username.value = newValue
      },
      label = "Username",
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

    Button(
      onClick = {
        Log.d("Login", "Login Button")
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