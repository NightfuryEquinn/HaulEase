package com.example.haulease.views.admin.inner

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.haulease.R
import com.example.haulease.models.Cargo
import com.example.haulease.ui.components.SimpleTextField
import com.example.haulease.validations.InputValidation.isValidInt
import com.example.haulease.viewmodels.admin.inner.AdminEditCargoVM
import com.example.haulease.viewmodels.admin.inner.AdminEditState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.list.ListDialog
import com.maxkeppeler.sheets.list.models.ListOption
import com.maxkeppeler.sheets.list.models.ListSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEditCargoScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit,
  cargoId: Int,
  shipmentId: Int,
  consignorId: Int,
  cargoType: String = "",
  cargoWeight: String = "",
  cargoLength: String = "",
  cargoWidth: String = "",
  cargoHeight: String = "",
  cargoImage: String = "",
  cargoDesc: String = "",
  adminEditCargoVM: AdminEditCargoVM = viewModel()
) {
  val context = LocalContext.current
  val cScope = rememberCoroutineScope()

  // Observer
  val adminEditState by adminEditCargoVM.adminEditState.collectAsState()

  BackHandler {
    onBack()
    navCtrl.navigate("AdminCargoDetail?cargoId=$cargoId&shipmentId=$shipmentId&consignorId=$consignorId") {
      launchSingleTop = true
    }
  }

  // State variables
  var type by remember { mutableStateOf(cargoType) }
  val weight = remember { mutableStateOf(cargoWeight) }
  val length = remember { mutableStateOf(cargoLength) }
  val width = remember { mutableStateOf(cargoWidth) }
  val height = remember { mutableStateOf(cargoHeight) }

  // Validations
  val allFieldsNotEmpty = type.isNotBlank()
      && weight.value.isNotBlank() && isValidInt(weight.value)
      && length.value.isNotBlank() && isValidInt(length.value)
      && width.value.isNotBlank() && isValidInt(width.value)
      && height.value.isNotBlank() && isValidInt(height.value)

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
        text = "Manage Cargo",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 48.sp,
          textAlign = TextAlign.End
        )
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    when (adminEditState) {
      is AdminEditState.SUCCESS -> {
        navCtrl.navigate("AdminCargoDetail?cargoId=$cargoId&shipmentId=$shipmentId&consignorId=$consignorId") {
          launchSingleTop = true
        }
      }
      is AdminEditState.LOADING -> {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
        )
      }
      is AdminEditState.INITIAL -> {
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
              text = when {
                displayOption.isNotBlank() -> displayOption
                cargoType.isNotBlank() -> cargoType
                else -> " - Select Cargo Type - "
              },
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

          Spacer(modifier = Modifier.height(20.dp))

          Row(
            modifier = Modifier
              .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Button(
              onClick = {
                cScope.launch {
                  try {
                    adminEditCargoVM.updateCargoDetail(
                      Cargo(
                        id = cargoId,
                        type = type,
                        weight = weight.value.toDouble(),
                        length = length.value.toDouble(),
                        width = width.value.toDouble(),
                        height = height.value.toDouble(),
                        image = cargoImage,
                        description = cargoDesc,
                        shipmentId = shipmentId
                      ),
                      context
                    )
                  } catch (e: Exception) {
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                  }
                }

                onBack()
                navCtrl.navigate("AdminCargoDetail?cargoId=$cargoId&shipmentId=$shipmentId&consignorId=$consignorId") {
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
                navCtrl.navigate("AdminCargoDetail?cargoId=$cargoId&shipmentId=$shipmentId&consignorId=$consignorId") {
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
        }
      }
    }

    Spacer(modifier = Modifier.padding(bottom = 52.dp))
  }
}