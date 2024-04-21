package com.example.haulease.models

import com.google.gson.annotations.SerializedName

data class Tracking(
  @SerializedName("trackingId")
  var id: Int,
  var time: String,
  var latitude: Double,
  var longitude: Double
)
