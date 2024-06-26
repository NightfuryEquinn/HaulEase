package com.example.haulease.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.haulease.R
import com.example.haulease.navigations.routes.SharedRoutes
import com.example.haulease.ui.components.SimpleTextField
import com.example.haulease.viewmodels.RegisterState
import com.example.haulease.viewmodels.RegisterVM

@Composable
fun RegisterScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit,
  registerVM: RegisterVM = viewModel()
) {
  val context = LocalContext.current

  // State variables
  val username = remember { mutableStateOf("") }
  val password = remember { mutableStateOf("") }
  val confirmPassword = remember { mutableStateOf("") }
  val email = remember { mutableStateOf("") }
  val contact = remember { mutableStateOf("")}
  val address = remember { mutableStateOf("") }
  var image by remember { mutableStateOf<Uri?>(null) }

  val company = remember { mutableStateOf("") }
  val companyEmail = remember { mutableStateOf("") }
  val companyAddress = remember { mutableStateOf("") }

  // Get image selection context
  val imageContext = LocalContext.current
  val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    image = uri
  }

  // Validations
  val isPasswordMatches = password.value == confirmPassword.value

  val allFieldsNotEmpty = username.value.isNotBlank()
      && password.value.isNotBlank()
      && confirmPassword.value.isNotBlank()
      && email.value.isNotBlank()
      && contact.value.isNotBlank()
      && address.value.isNotBlank()
      && (image != null)

  // Observer
  val registerState by registerVM.registerState.collectAsState()

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
        isSingle = true,
        isSensitive = true
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
        isSingle = true,
        isSensitive = true
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
        isSingle = true
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = contact,
        onValueChange = { newValue ->
          contact.value = newValue
        },
        label = "Contact Number",
        isSingle = true,
        onlyNumber = true
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
        maxLines = 5
      )

      Spacer(modifier = Modifier.height(8.dp))

      Image(
        painter =
        if (image != null) {
          rememberAsyncImagePainter(
            model = ImageRequest.Builder(imageContext)
              .crossfade(false)
              .data(image)
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
        inputText = company,
        onValueChange = { newValue ->
          company.value = newValue
        },
        label = "Company Name (optional)",
        isSingle = true
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
        isSingle = true
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
        maxLines = 5
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text(
      text = "NOTE: Ensure that your details are correct as you will not be able to change them later.",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.librebold)),
        fontSize = 12.sp,
        textDecoration = TextDecoration.Underline,
        textAlign = TextAlign.Justify
      )
    )

    Spacer(modifier = Modifier.height(16.dp))

    Image(
      painter = painterResource(id = R.drawable.cargo),
      contentDescription = "Freight",
      modifier = Modifier
        .size(64.dp)
        .align(Alignment.CenterHorizontally),
    )

    Spacer(modifier = Modifier.height(8.dp))

    when (registerState) {
      is RegisterState.SUCCESS -> {
        navCtrl.navigate(SharedRoutes.Login.routes) {
          launchSingleTop = true
        }
      }
      is RegisterState.LOADING -> {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
        )
      }
      is RegisterState.INITIAL -> {
        Button(
          onClick = {
            registerVM.registerConsignor(
              username = username.value,
              password = password.value,
              email = email.value,
              contact = contact.value,
              address = address.value,
              avatar = image!!,
              company = company.value,
              companyEmail = companyEmail.value,
              companyAddress = companyAddress.value,
              context = context
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = 80.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311)),
          shape = RoundedCornerShape(5.dp),
          enabled = allFieldsNotEmpty && isPasswordMatches
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
      else -> {}
    }
  }
}