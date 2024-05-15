package com.example.haulease.models

import com.google.gson.annotations.SerializedName

data class Cargo(
  @SerializedName("cargoId")
  var id: Int,
  var type: String,
  var weight: Double,
  var length: Double,
  var width: Double,
  var height: Double,
  var image: String,
  var description: String,
  var shipmentId: Int?,
)
