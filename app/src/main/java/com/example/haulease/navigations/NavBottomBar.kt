package com.example.haulease.navigations

import com.example.haulease.R

object NavBottomBar {
  val BottomBarItems = listOf(
    BottomBarItem(
      title = "DASHBOARD",
      image = R.drawable.dashboard,
      route = "Dashboard"
    ),
    BottomBarItem(
      title = "SHIPMENT",
      image = R.drawable.box,
      route = "Shipment"
    ),
    BottomBarItem(
      title = "HISTORY",
      image = R.drawable.history,
      route = "History"
    ),
    BottomBarItem(
      title = "PROFILE",
      image = R.drawable.profile,
      route = "Profile"
    ),
  )
}