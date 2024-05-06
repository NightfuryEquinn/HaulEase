package com.example.haulease.navigations.routes

sealed class AdminRoutes(val routes: String) {
  data object AdminDashboard: AdminRoutes("AdminDashboard")
  data object AdminShipment: AdminRoutes("AdminShipment")
  data object AdminHistory: AdminRoutes("AdminHistory")
  data object AdminProfile: AdminRoutes("AdminProfile")
}