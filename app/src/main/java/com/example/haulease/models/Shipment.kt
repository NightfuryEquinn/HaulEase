package com.example.haulease.models

import com.google.gson.annotations.SerializedName

data class Shipment(
  @SerializedName("shipmentId")
  var id: Int,
  var status: String,
  var origin: String,
  var destination: String
)
