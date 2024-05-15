package com.example.haulease.models

import com.google.gson.annotations.SerializedName

data class Truck(
  @SerializedName("truckId")
  var id: Int,
  var driverName: String,
  var licensePlate: String
)
