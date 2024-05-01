package com.example.haulease.navigations.routes

sealed class UserInnerRoutes(val routes: String) {
  data object ShipmentDetail: UserInnerRoutes("ShipmentDetail")
  data object CargoDetail: UserInnerRoutes("CargoDetail")
  data object CreateShipment: UserInnerRoutes("CreateShipment")
  data object CreateCargo: UserInnerRoutes("CreateCargo")
  data object Payment: UserInnerRoutes("Payment")
}