package com.example.haulease.navigations.routes

sealed class UserRoutes(val routes: String) {
  data object Dashboard: UserRoutes("Dashboard")
  data object Shipment: UserRoutes("Shipment")
  data object History: UserRoutes("History")
  data object Profile: UserRoutes("Profile")
}