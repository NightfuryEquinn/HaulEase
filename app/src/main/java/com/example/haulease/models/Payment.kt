package com.example.haulease.models

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class Payment(
  @SerializedName("paymentId")
  var id: Int,
  var first: Double,
  var second: Double,
  var final: Double
)
