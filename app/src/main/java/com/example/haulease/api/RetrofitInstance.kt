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

  val getApi: Get by lazy {
    retrofit.create(Get::class.java)
  }

  val postApi: Post by lazy {
    retrofit.create(Post::class.java)
  }

  val putApi: Put by lazy {
    retrofit.create(Put::class.java)
  }

  val deleteApi: Delete by lazy {
    retrofit.create(Delete::class.java)
  }
}