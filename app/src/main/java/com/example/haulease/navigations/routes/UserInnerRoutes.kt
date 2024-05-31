package com.example.haulease.navigations.routes

sealed class UserInnerRoutes(val routes: String) {
  data object ShipmentDetail: UserInnerRoutes("ShipmentDetail?shipmentId={shipmentId}")
  data object CargoDetail: UserInnerRoutes("CargoDetail?cargoId={cargoId}&shipmentId={shipmentId}")
  data object CreateShipment: UserInnerRoutes("CreateShipment")
  data object CreateCargo: UserInnerRoutes("CreateCargo")
  data object Payment: UserInnerRoutes("Payment?paymentId={paymentId}&shipmentId={shipmentId}")
}