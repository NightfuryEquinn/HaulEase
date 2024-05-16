package com.example.haulease.models

import java.util.UUID

data class TempShipmentCargo(
  var id: UUID,
  var cargoList: MutableList<Cargo>
)