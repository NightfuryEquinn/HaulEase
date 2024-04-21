package com.example.haulease.api

import com.example.haulease.api.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
  private val retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  val api: HaulEaseApi by lazy {
    retrofit.create(HaulEaseApi::class.java)
  }
}