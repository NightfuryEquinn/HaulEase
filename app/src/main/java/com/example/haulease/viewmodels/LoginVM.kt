package com.example.haulease.viewmodels

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Consignor
import com.example.haulease.models.Sessions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Login status
sealed class LoginState {
  data object INITIAL: LoginState()
  data object LOADING: LoginState()
  data object SUCCESS: LoginState()
  data object SUCCESSADMIN: LoginState()
}

class LoginVM: ViewModel() {
  private val repository: Repository = Repository()

  private var theUser: Consignor? = null
  private val _loginState = MutableStateFlow<LoginState>(LoginState.INITIAL)

  // Set observer value
  val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

  // Check if user exists
  private suspend fun checkUserExists(
    email: String,
    password: String
  ): Boolean {
    val res = repository.checkConsignor(email, password)

    if (res.isSuccessful) {
      res.body()?.let {
        theUser = it
      }

      return res.isSuccessful
    }

    return false
  }

  // Login existing consignor using Firebase
  fun loginConsignor(
    email: String,
    password: String,
    context: android.content.Context
  ) {
    _loginState.value = LoginState.LOADING

    viewModelScope.launch {
      try {
        if (checkUserExists(email, password)) {
          FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()

          Sessions.sessionToken = theUser?.id.toString()
          Sessions.sessionEmail = theUser?.email.toString()
          Sessions.sessionRole = theUser?.role.toString()

          if (theUser?.role == "Admin") {
            _loginState.value = LoginState.SUCCESSADMIN
          } else {
            _loginState.value = LoginState.SUCCESS
          }
        } else {
          Toast.makeText(context, "User does not exist.", Toast.LENGTH_LONG).show()
          _loginState.value = LoginState.INITIAL
        }
      } catch (e: Exception) {
        Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
        _loginState.value = LoginState.INITIAL
      }
    }
  }
}