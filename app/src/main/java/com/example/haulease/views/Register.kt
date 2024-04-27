package com.example.haulease.views

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.haulease.R
import com.example.haulease.navigations.SharedRoutes
import com.example.haulease.ui.components.SimpleTextField
import com.example.haulease.validations.InputValidation

@Composable
fun RegisterScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit
) {
  // State variables
  val username = remember { mutableStateOf("") }
  val password = remember { mutableStateOf("") }
  val confirmPassword = remember { mutableStateOf("") }
  val email = remember { mutableStateOf("") }
  val address = remember { mutableStateOf("") }
  val image = remember { mutableStateOf<Uri?>(null) }

  val companyName = remember { mutableStateOf("") }
  val companyEmail = remember { mutableStateOf("") }
  val companyAddress = remember { mutableStateOf("") }

  val imageContext = LocalContext.current
  val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    image.value = uri
  }

  // Validations
  val isEmailValid by remember { mutableStateOf(InputValidation.isValidEmail(email.value))}
  val isPasswordValid by remember { mutableStateOf(InputValidation.isValidPassword(password.value))}

  val allFieldsNotEmpty = username.value.isNotBlank()
      && password.value.isNotBlank()
      && confirmPassword.value.isNotBlank()
      && email.value.isNotBlank()
      && address.value.isNotBlank()
      && (image.value != null)

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
      text = "Register As A",
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
          onBack()
          navCtrl.navigate(SharedRoutes.Login.routes)
        }
      ),
      text = "Login to existing account",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.libre)),
        fontSize = 12.sp,
        textDecoration = TextDecoration.Underline
      )
    )

    Spacer(modifier = Modifier.height(32.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .weight(1f)
    ) {
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
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = confirmPassword,
        onValueChange = { newValue ->
          confirmPassword.value = newValue
        },
        label = "Confirm Password",
      )

      Spacer(modifier = Modifier.height(8.dp))

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
        inputText = address,
        onValueChange = { newValue ->
          address.value = newValue
        },
        label = "Residential Address",
      )

      Spacer(modifier = Modifier.height(8.dp))

      Image(
        painter =
        if (image.value != null) {
          rememberAsyncImagePainter(
            model = ImageRequest.Builder(imageContext)
              .crossfade(false)
              .data(image.value)
              .build(),
            filterQuality = FilterQuality.High
          )
        } else {
          painterResource(id = R.drawable.image)
        },
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp)
          .height(160.dp)
          .clickable {
            launcher.launch("image/*")
          }
      )

      Text(
        text = "Profile Picture",
        style = TextStyle.Default.copy(
          fontFamily = FontFamily(Font(R.font.libre))
        ),
        modifier = Modifier
          .padding(top = 8.dp)
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = companyName,
        onValueChange = { newValue ->
          companyName.value = newValue
        },
        label = "Company Name (optional)",
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = companyEmail,
        onValueChange = { newValue ->
          companyEmail.value = newValue
        },
        label = "Company Email (optional)",
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = companyAddress,
        onValueChange = { newValue ->
          companyAddress.value = newValue
        },
        label = "Company Address (optional)",
      )
    }

    Spacer(modifier = Modifier.height(32.dp))

    Image(
      painter = painterResource(id = R.drawable.cargo),
      contentDescription = "Freight",
      modifier = Modifier
        .size(64.dp)
        .align(Alignment.CenterHorizontally),
    )

    Spacer(modifier = Modifier.height(8.dp))

    Button(
      onClick = {
        Log.d("Register", "Register Button")
      },
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally)
        .padding(horizontal = 80.dp),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311)),
      shape = RoundedCornerShape(5.dp),
      enabled = isEmailValid && isPasswordValid && allFieldsNotEmpty
    ) {
      Text(
        text = "Register",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 24.sp,
        )
      )
    }
  }
}