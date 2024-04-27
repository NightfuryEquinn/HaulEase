package com.example.haulease.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.haulease.R

@Composable
fun SimpleTextField(
  modifier: Modifier = Modifier,
  inputText: MutableState<String>,
  onValueChange: (String) -> Unit,
  label: String,
  isSensitive: Boolean = false,
  isEnabled: Boolean = false,
  isSingle: Boolean = false,
  maxLines: Int = 1
) {
  // Access keyboard and focus manager
  val keyboardCtrl = LocalSoftwareKeyboardController.current
  val focusManager = LocalFocusManager.current

  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    OutlinedTextField(
      modifier = modifier,
      value = inputText.value,
      onValueChange = {
        inputText.value = it
        onValueChange(it)
      },
      visualTransformation = if (isSensitive) {
        PasswordVisualTransformation()
      } else {
        VisualTransformation.None
      },
      enabled = !isEnabled,
      singleLine = isSingle,
      maxLines = maxLines,
      textStyle = TextStyle.Default.copy(fontFamily = FontFamily(Font(R.font.libre))),
      keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
      ),
      keyboardActions = KeyboardActions(
        onDone = {
          keyboardCtrl?.hide()
          focusManager.clearFocus()
        },
      ),
    )

    Text(
      text = label,
      style = TextStyle.Default.copy(
        fontFamily = FontFamily(Font(R.font.libre))
      )
    )
  }
}