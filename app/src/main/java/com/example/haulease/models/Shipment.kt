package com.example.haulease.models

import com.google.gson.annotations.SerializedName

data class Shipment(
  @SerializedName("shipmentId")
  var id: Int,
  var status: String,
  var origin: String,
  var destination: String,
  var receiverName: String,
  var receiverContact: String,
  var consignorId: Int?,
  var paymentId: Int?,
  var trackingId: Int?,
  var truckId: Int?
)
