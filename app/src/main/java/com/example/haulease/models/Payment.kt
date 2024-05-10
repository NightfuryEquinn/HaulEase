package com.example.haulease.models

import com.google.gson.annotations.SerializedName

data class Payment(
  @SerializedName("paymentId")
  var id: Int,
  var first: Double?,
  var second: Double?,
  var final: Double?
)
