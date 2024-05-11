package com.example.haulease.viewmodels

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Consignor
import com.example.haulease.validations.InputValidation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Forgot status
sealed class ForgotState {
  data object INITIAL: ForgotState()
  data object LOADING: ForgotState()
  data object SUCCESS: ForgotState()
}

class ForgotVM: ViewModel() {
  private val repository: Repository = Repository()

  private var theUser: Consignor? = null
  private val _forgotState = MutableStateFlow<ForgotState>(ForgotState.INITIAL)

  // Set observer value
  val forgotState: StateFlow<ForgotState> = _forgotState

  // Check if email is valid
  private suspend fun checkEmailExists(
    email: String
  ): Boolean {
    val res = repository.checkConsignorEmail(email)

    if (res.isSuccessful) {
      res.body()?.let {
        theUser = it
      }

      return res.isSuccessful
    }

    return false
  }

  // Update password on AWS
  private suspend fun updatePassword(
    password: String
  ): Boolean {
    if (theUser != null) {
      theUser!!.password = password
      val res = repository.putConsignor(theUser!!.id, theUser!!)

      return res.isSuccessful
    }

    return false
  }

  // Send reset password email
  fun resetConsignor(
    email: String,
    password: String,
    confirmPassword: String,
    context: android.content.Context
  ) {
    _forgotState.value = ForgotState.LOADING

    viewModelScope.launch {
      try {
        when {
          !InputValidation.isValidPassword(password) -> {
            Toast.makeText(context, "Password must be 8 alphanumeric characters long.", Toast.LENGTH_LONG).show()
            _forgotState.value = ForgotState.INITIAL
          }

          password != confirmPassword -> {
            Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_LONG).show()
            _forgotState.value = ForgotState.INITIAL
          }

          !InputValidation.isValidEmail(email) -> {
            Toast.makeText(context, "Please enter a valid email address.", Toast.LENGTH_LONG).show()
            _forgotState.value = ForgotState.INITIAL
          }

          else -> {
            if (checkEmailExists(email)) {
              FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                  Toast.makeText(context, "Reset email sent. Please check email.", Toast.LENGTH_LONG).show()
                  _forgotState.value = ForgotState.SUCCESS
                }
                .addOnFailureListener {
                  Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
                  _forgotState.value = ForgotState.INITIAL
                }

              updatePassword(password)
            }
          }
        }
      } catch (e: Exception) {
        Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
        _forgotState.value = ForgotState.INITIAL
      }
    }
  }
}