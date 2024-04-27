package com.example.haulease.validations

object InputValidation {
  // Function to validate email address
  fun isValidEmail(email: String) : Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  // Function to validate password (at least 8 characters)
  fun isValidPassword(password: String): Boolean {
    return password.length >= 8
  }
}
