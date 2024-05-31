package com.example.haulease.navigations.routes

sealed class AdminInnerRoutes(val routes: String) {
  data object AdminShipmentDetail: AdminInnerRoutes("AdminShipmentDetail?shipmentId={shipmentId}&consignorId={consignorId}")
  data object AdminCargoDetail: AdminInnerRoutes("AdminCargoDetail?cargoId={cargoId}&shipmentId={shipmentId}&consignorId={consignorId}")
  data object AdminEditCargo: AdminInnerRoutes("EditCargo?cargoId={cargoId}&shipmentId={shipmentId}&consignorId={consignorId}&type={type}&weight={weight}&length={length}&width={width}&height={height}&image={image}&desc={desc}")
}