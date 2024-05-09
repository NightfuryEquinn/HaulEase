package com.example.haulease.api

import com.example.haulease.models.Cargo
import com.example.haulease.models.Consignor
import com.example.haulease.models.Payment
import com.example.haulease.models.Shipment
import com.example.haulease.models.Tracking
import com.example.haulease.models.Truck
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Post {
  @POST("cargo")
  suspend fun postCargo(
    @Body cargo: Cargo
  ): Response<Cargo>

  @POST("consignor")
  suspend fun postConsignor(
    @Body consignor: Consignor
  ): Response<Consignor>

  @POST("payment")
  suspend fun postPayment(
    @Body payment: Payment
  ): Response<Payment>

  @POST("shipment")
  suspend fun postShipment(
    @Body shipment: Shipment
  ): Response<Shipment>

  @POST("tracking")
  suspend fun postTracking(
    @Body tracking: Tracking
  ): Response<Tracking>

  @POST("truck")
  suspend fun postTruck(
    @Body truck: Truck
  ): Response<Truck>
}