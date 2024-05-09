package com.example.haulease.api

import com.example.haulease.models.Cargo
import com.example.haulease.models.Consignor
import com.example.haulease.models.Payment
import com.example.haulease.models.Shipment
import com.example.haulease.models.Tracking
import com.example.haulease.models.Truck
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface Put {
  @PUT("cargo/{cargoId}")
  suspend fun putCargo(
    @Path("cargoId") cargoId: Int,
    @Body cargo: Cargo
  ): Response<Cargo>

  @PUT("consignor/{consignorId}")
  suspend fun putConsignor(
    @Path("consignorId") consignorId: Int,
    @Body consignor: Consignor
  ): Response<Consignor>

  @PUT("payment/{paymentId}")
  suspend fun putPayment(
    @Path("paymentId") paymentId: Int,
    @Body payment: Payment
  ): Response<Payment>

  @PUT("shipment/{shipmentId}")
  suspend fun putShipment(
    @Path("shipmentId") shipmentId: Int,
    @Body shipment: Shipment
  ): Response<Shipment>

  @PUT("tracking/{trackingId}")
  suspend fun putTracking(
    @Path("trackingId") trackingId: Int,
    @Body tracking: Tracking
  ): Response<Tracking>

  @PUT("truck/{truckId}")
  suspend fun putTruck(
    @Path("truckId") truckId: Int,
    @Body truck: Truck
  ): Response<Truck>
}