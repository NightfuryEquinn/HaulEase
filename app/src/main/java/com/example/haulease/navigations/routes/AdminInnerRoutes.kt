package com.example.haulease.navigations.routes

sealed class AdminInnerRoutes(val routes: String) {
  data object AdminShipmentDetail: AdminInnerRoutes("ShipmentDetail")
  data object AdminCargoDetail: AdminInnerRoutes("CargoDetail")
  data object AdminEditCargo: AdminInnerRoutes("EditCargo")
}