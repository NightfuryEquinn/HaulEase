package com.example.haulease.views.user.inner

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.haulease.R
import com.example.haulease.models.Cargo
import com.example.haulease.ui.components.SimpleTextField
import com.example.haulease.validations.InputValidation.isValidInt
import com.example.haulease.viewmodels.user.inner.CreateCargoShipmentVM
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.list.ListDialog
import com.maxkeppeler.sheets.list.models.ListOption
import com.maxkeppeler.sheets.list.models.ListSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCargoScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit,
  shipmentId: Int?,
  createCargoShipmentVM: CreateCargoShipmentVM
) {
  val context = LocalContext.current
  val cScope = rememberCoroutineScope()

  // State variables
  var type by remember { mutableStateOf("") }
  val weight = remember { mutableStateOf("") }
  val length = remember { mutableStateOf("") }
  val width = remember { mutableStateOf("") }
  val height = remember { mutableStateOf("") }
  val image = remember { mutableStateOf<Uri?>(null) }
  val desc = remember { mutableStateOf("")}

  // Get image selection context
  val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    image.value = uri
  }

  // Validations
  val allFieldsNotEmpty = type.isNotBlank()
      && weight.value.isNotBlank() && isValidInt(weight.value)
      && length.value.isNotBlank() && isValidInt(length.value)
      && width.value.isNotBlank() && isValidInt(width.value)
      && height.value.isNotBlank() && isValidInt(height.value)
      && (image.value != null)
      && desc.value.isNotBlank()

  // Option dialog for priority
  val listOptionState = rememberUseCaseState()
  var displayOption by remember { mutableStateOf("") }

  val options = listOf(
    ListOption(titleText = "Light (0 - 25kg)"),
    ListOption(titleText = "Medium (26 - 50kg)"),
    ListOption(titleText = "Heavy (51 - 100kg)"),
    ListOption(titleText = "Massive (100kg+)")
  )

  ListDialog(
    state = listOptionState,
    selection = ListSelection.Single(
      options = options
    ) { _, option ->
      type = option.titleText
      displayOption = option.titleText
    },
  )

  BackHandler {
    onBack()
    navCtrl.navigate("CreateShipment?shipmentId=$shipmentId") {
      launchSingleTop = true
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
        text = "Create Cargo",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 48.sp,
          textAlign = TextAlign.End
        )
      )
    }

    Spacer(modifier = Modifier.height(25.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .weight(1f)
    ) {
      Button(
        onClick = {
          listOptionState.show()
        },
        modifier = Modifier
          .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color(0xFF14213D)),
        shape = RoundedCornerShape(5.dp),
      ) {
        Text(
          text = displayOption.ifBlank { " - Select Cargo Type - " },
          style = TextStyle(
            color = Color(0xFFE5E5E5),
            fontFamily = FontFamily(Font(R.font.librebold))
          )
        )
      }

      Text(
        text = "Type",
        style = TextStyle.Default.copy(
          fontFamily = FontFamily(Font(R.font.libre))
        )
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = weight,
        onValueChange = { newValue ->
          weight.value = newValue
        },
        label = "Weight in estimation",
        onlyNumber = true
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = length,
        onValueChange = { newValue ->
          length.value = newValue
        },
        label = "Length in estimation",
        onlyNumber = true
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = width,
        onValueChange = { newValue ->
          width.value = newValue
        },
        label = "Width in estimation",
        onlyNumber = true
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = height,
        onValueChange = { newValue ->
          height.value = newValue
        },
        label = "Height in estimation",
        onlyNumber = true
      )

      Spacer(modifier = Modifier.height(8.dp))

      Image(
        painter =
        if (image.value != null) {
          rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
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
          .height(250.dp)
          .clickable {
            launcher.launch("image/*")
          }
      )

      Text(
        text = "Cargo Image",
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
        inputText = desc,
        onValueChange = { newValue ->
          desc.value = newValue
        },
        label = "Description (optional)",
        maxLines = 5
      )
    }

    Spacer(modifier = Modifier.height(30.dp))

    Text(
      text = "NOTE: The weight and dimension of the cargo will be remeasured once arrived at the harbor.",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.librebold)),
        fontSize = 12.sp,
        textDecoration = TextDecoration.Underline
      )
    )

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = {
          createCargoShipmentVM.appendCargo(
            Cargo(
              id = 0,
              type = type,
              weight = weight.value.toDouble(),
              length = length.value.toDouble(),
              width = width.value.toDouble(),
              height = height.value.toDouble(),
              image = image.value.toString(),
              description = desc.value,
              shipmentId = 0
            )
          )

          onBack()
          navCtrl.navigate("CreateShipment?shipmentId=$shipmentId") {
            launchSingleTop = true
          }
        },
        modifier = Modifier
          .weight(0.35f),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
        shape = RoundedCornerShape(5.dp),
        enabled = allFieldsNotEmpty
      ) {
        Text(
          text = "Save",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 24.sp,
            color = Color(0xFFE5E5E5)
          )
        )
      }

      Spacer(modifier = Modifier.width(20.dp))

      Button(
        onClick = {
          onBack()
          navCtrl.navigate("CreateShipment?shipmentId=$shipmentId") {
            launchSingleTop = true
          }
        },
        modifier = Modifier
          .weight(0.35f),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA111)),
        shape = RoundedCornerShape(5.dp),
      ) {
        Text(
          text = "Cancel",
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