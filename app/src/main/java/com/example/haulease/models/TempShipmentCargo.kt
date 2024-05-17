package com.example.haulease.models

data class TempShipmentCargo(
  var receiverName: String,
  var receiverContact: String,
  var cargoList: MutableList<Cargo>
)