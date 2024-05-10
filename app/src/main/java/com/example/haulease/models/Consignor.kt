package com.example.haulease.models

import com.google.gson.annotations.SerializedName

data class Consignor(
  @SerializedName("consignorId")
  var id: Int,
  var username: String,
  var avatar: String,
  var contact: String,
  var email: String,
  var password: String,
  var address: String,
  var company: String?,
  var companyEmail: String?,
  var companyAddress: String?,
  var role: String
)
