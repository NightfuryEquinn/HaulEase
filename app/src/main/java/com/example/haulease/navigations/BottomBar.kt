package com.example.haulease.navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalShipping

object BottomBar {
  val BottomBarItems = listOf(
    BottomBarItem(
      title = "DASHBOARD",
      image = Icons.Filled.Dashboard,
      route = "Dashboard",
      adminRoute = "AdminDashboard"
    ),
    BottomBarItem(
      title = "SHIPMENT",
      image = Icons.Filled.LocalShipping,
      route = "Shipment",
      adminRoute = "AdminShipment"
    ),
    BottomBarItem(
      title = "HISTORY",
      image = Icons.Filled.History,
      route = "History",
      adminRoute = "AdminHistory"
    ),
    BottomBarItem(
      title = "PROFILE",
      image = Icons.Filled.AccountCircle,
      route = "Profile",
      adminRoute = "AdminProfile"
    ),
  )
}