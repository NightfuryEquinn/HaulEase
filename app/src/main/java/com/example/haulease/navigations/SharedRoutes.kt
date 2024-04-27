package com.example.haulease.navigations

sealed class SharedRoutes(val routes: String) {
  data object Login: SharedRoutes("Login")
  data object Register: SharedRoutes("Register")
  data object Forgot: SharedRoutes("Forgot")
}