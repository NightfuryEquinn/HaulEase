package com.example.haulease.api

import com.example.haulease.models.Cargo
import com.example.haulease.models.Consignor
import com.example.haulease.models.Payment
import com.example.haulease.models.Shipment
import com.example.haulease.models.Tracking
import com.example.haulease.models.Truck
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface Delete {
  @DELETE("cargo/{cargoId}")
  suspend fun deleteCargo(
    @Path("cargoId") cargoId: Int
  ): Response<Cargo>

  @DELETE("consignor/{consignorId}")
  suspend fun deleteConsignor(
    @Path("consignorId") consignorId: Int
  ): Response<Consignor>

  @DELETE("payment/{paymentId}")
  suspend fun deletePayment(
    @Path("paymentId") paymentId: Int
  ): Response<Payment>

  @DELETE("shipment/{shipmentId}")
  suspend fun deleteShipment(
    @Path("shipmentId") shipmentId: Int
  ): Response<Shipment>

  @DELETE("tracking/{trackingId}")
  suspend fun deleteTracking(
    @Path("trackingId") trackingId: Int
  ): Response<Tracking>

  @DELETE("truck/{truckId}")
  suspend fun deleteTruck(
    @Path("truckId") truckId: Int
  ): Response<Truck>
}