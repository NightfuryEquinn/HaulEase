package com.example.haulease.viewmodels

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Consignor
import com.example.haulease.validations.InputValidation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

// Registration status
sealed class RegisterState {
  data object INITIAL: RegisterState()
  data object LOADING: RegisterState()
  data object SUCCESS: RegisterState()
}

class RegisterVM: ViewModel() {
  private val fireAuth = FirebaseAuth.getInstance()
  private val repository: Repository = Repository()

  private var avatarUrl: String = ""
  private val _registerState = MutableStateFlow<RegisterState>(RegisterState.INITIAL)

  // Set observer value
  val registerState: StateFlow<RegisterState> = _registerState

  // Upload image to Firebase storage
  private suspend fun uploadImage(
    avatar: Uri,
    context: android.content.Context
  ): String {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference.child("images/${UUID.randomUUID()}.jpg")
    val uploadTask = storageRef.putFile(avatar)

    val downloadUrl = try {
      uploadTask.await()
      storageRef.downloadUrl.await().toString()
    } catch (e: Exception) {
      Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
      return ""
    }

    return downloadUrl
  }

  // Register a new consignor to Firebase Auth and AWS
  fun registerConsignor(
    username: String,
    password: String,
    email: String,
    contact: String,
    address: String,
    avatar: Uri?,
    company: String? = "",
    companyEmail: String? = "",
    companyAddress: String? = "",
    context: android.content.Context
  ) {
    _registerState.value = RegisterState.LOADING

    viewModelScope.launch {
      try {
        when {
          !InputValidation.isValidPassword(password) -> {
            Toast.makeText(context, "Password must be 8 alphanumeric characters long.", Toast.LENGTH_LONG).show()
            _registerState.value = RegisterState.INITIAL
          }

          !InputValidation.isValidEmail(email) -> {
            Toast.makeText(context, "Please enter a valid email address.", Toast.LENGTH_LONG).show()
            _registerState.value = RegisterState.INITIAL
          }

          !InputValidation.isValidContact(contact) -> {
            Toast.makeText(context, "Contact must be 10 to 15 digits long.", Toast.LENGTH_LONG).show()
            _registerState.value = RegisterState.INITIAL
          }

          else -> {
            // Firebase authentication with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()

            if (avatar != null) {
              avatarUrl = uploadImage(avatar, context)

              val newConsignor = Consignor(
                id = 0,
                username = username,
                password = password,
                email = email,
                contact = contact,
                address = address,
                avatar = avatarUrl,
                company = company,
                companyEmail = companyEmail,
                companyAddress = companyAddress,
                role = "User"
              )

              // TODO repository.postConsignor(newConsignor)
              Toast.makeText(context, "Account is created. Please login.", Toast.LENGTH_LONG).show()
              _registerState.value = RegisterState.SUCCESS
            } else {
              Toast.makeText(context, "No avatar URL available.", Toast.LENGTH_LONG).show()
              _registerState.value = RegisterState.INITIAL
            }
          }
        }
      } catch (e: Exception) {
        Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
        _registerState.value = RegisterState.INITIAL
      }
    }
  }
}

