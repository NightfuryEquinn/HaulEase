package com.example.haulease.navigations.routes

sealed class AdminInnerRoutes(val routes: String) {
  data object AdminShipmentDetail: AdminInnerRoutes("AdminShipmentDetail")
  data object AdminCargoDetail: AdminInnerRoutes("AdminCargoDetail")
  data object AdminEditCargo: AdminInnerRoutes("EditCargo")
}