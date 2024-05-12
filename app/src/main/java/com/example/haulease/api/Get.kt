package com.example.haulease.api

import com.example.haulease.models.Cargo
import com.example.haulease.models.Consignor
import com.example.haulease.models.Payment
import com.example.haulease.models.Shipment
import com.example.haulease.models.Tracking
import com.example.haulease.models.Truck
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Get {
  @GET("cargo")
  suspend fun getCargos(): Response<List<Cargo>>

  @GET("cargo/{cargoId}")
  suspend fun getCargo(
    @Path("cargoId") cargoId: Int
  ): Response<Cargo>

  @GET("consignor")
  suspend fun getConsignors(): Response<List<Consignor>>

  @GET("consignor/email/{email}/password/{password}")
  suspend fun checkConsignor(
    @Path("email") email: String,
    @Path("password") password: String
  ): Response<Consignor>

  @GET("consignor/email/{email}")
  suspend fun checkConsignorEmail(
    @Path("email") email: String
  ): Response<Consignor>

  @GET("consignor/{consignorId}")
  suspend fun getConsignor(
    @Path("consignorId") consignorId: Int
  ): Response<Consignor>

  @GET("payment")
  suspend fun getPayments(): Response<List<Payment>>

  @GET("payment/{paymentId}")
  suspend fun getPayment(
    @Path("paymentId") paymentId: Int
  ): Response<Payment>

  @GET("shipment")
  suspend fun getShipments(): Response<List<Shipment>>

  @GET("shipment/consignor/{consignorId}")
  suspend fun getShipmentsByConsignor(
    @Path("consignorId") consignorId: Int
  ): Response<List<Shipment>>

  @GET("shipment/{shipmentId}")
  suspend fun getShipment(
    @Path("shipmentId") shipmentId: Int
  ): Response<Shipment>

  @GET("tracking")
  suspend fun getTrackings(): Response<List<Tracking>>

  @GET("tracking/{trackingId}")
  suspend fun getTracking(
    @Path("trackingId") trackingId: Int
  ): Response<Tracking>

  @GET("truck")
  suspend fun getTrucks(): Response<List<Truck>>

  @GET("truck/{truckId}")
  suspend fun getTruck(
    @Path("truckId") truckId: Int
  ): Response<Truck>
}