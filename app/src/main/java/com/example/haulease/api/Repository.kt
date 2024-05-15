package com.example.haulease.api

import com.example.haulease.models.Cargo
import com.example.haulease.models.Consignor
import com.example.haulease.models.Payment
import com.example.haulease.models.Shipment
import com.example.haulease.models.ShipmentPayment
import com.example.haulease.models.ShipmentTracking
import com.example.haulease.models.ShipmentTruck
import com.example.haulease.models.Tracking
import com.example.haulease.models.Truck
import retrofit2.Response

class Repository {
  /**
   * Cargo
   */
  suspend fun getCargos(): Response<List<Cargo>> {
    return RetrofitInstance.getApi.getCargos()
  }

  suspend fun getCargosByShipment(id: Int): Response<List<Cargo>> {
    return RetrofitInstance.getApi.getCargosByShipment(id)
  }

  suspend fun getCargo(id: Int): Response<Cargo> {
    return RetrofitInstance.getApi.getCargo(id)
  }

  suspend fun postCargo(cargo: Cargo): Response<Cargo> {
    return RetrofitInstance.postApi.postCargo(cargo)
  }

  suspend fun putCargo(id: Int, cargo: Cargo): Response<Cargo> {
    return RetrofitInstance.putApi.putCargo(id, cargo)
  }

  suspend fun deleteCargo(id: Int): Response<Cargo> {
    return RetrofitInstance.deleteApi.deleteCargo(id)
  }

  /**
   * Consignor
   */
  suspend fun getConsignors(): Response<List<Consignor>> {
    return RetrofitInstance.getApi.getConsignors()
  }

  suspend fun checkConsignor(email: String, password: String): Response<Consignor> {
    return RetrofitInstance.getApi.checkConsignor(email, password)
  }

  suspend fun checkConsignorEmail(email: String): Response<Consignor> {
    return RetrofitInstance.getApi.checkConsignorEmail(email)
  }

  suspend fun getConsignor(id: Int): Response<Consignor> {
    return RetrofitInstance.getApi.getConsignor(id)
  }

  suspend fun postConsignor(consignor: Consignor): Response<Consignor> {
    return RetrofitInstance.postApi.postConsignor(consignor)
  }

  suspend fun putConsignor(id: Int, consignor: Consignor): Response<Consignor> {
    return RetrofitInstance.putApi.putConsignor(id, consignor)
  }

  suspend fun deleteConsignor(id: Int): Response<Consignor> {
    return RetrofitInstance.deleteApi.deleteConsignor(id)
  }

  /**
   * Payment
   */
  suspend fun getPayments(): Response<List<Payment>> {
    return RetrofitInstance.getApi.getPayments()
  }

  suspend fun getPayment(id: Int): Response<Payment> {
    return RetrofitInstance.getApi.getPayment(id)
  }

  suspend fun postPayment(payment: Payment): Response<Payment> {
    return RetrofitInstance.postApi.postPayment(payment)
  }

  suspend fun putPayment(id: Int, payment: Payment): Response<Payment> {
    return RetrofitInstance.putApi.putPayment(id, payment)
  }

  suspend fun deletePayment(id: Int): Response<Payment> {
    return RetrofitInstance.deleteApi.deletePayment(id)
  }

  /**
   * Shipment
   */
  suspend fun getShipments(): Response<List<Shipment>> {
    return RetrofitInstance.getApi.getShipments()
  }

  suspend fun getShipmentsByConsignor(id: Int): Response<List<Shipment>> {
    return RetrofitInstance.getApi.getShipmentsByConsignor(id)
  }

  suspend fun getShipmentPayment(id: Int): Response<List<ShipmentPayment>> {
    return RetrofitInstance.getApi.getShipmentPayment(id)
  }

  suspend fun getShipmentTracking(id: Int): Response<List<ShipmentTracking>> {
    return RetrofitInstance.getApi.getShipmentTracking(id)
  }

  suspend fun getShipmentTruck(id: Int): Response<List<ShipmentTruck>> {
    return RetrofitInstance.getApi.getShipmentTruck(id)
  }

  suspend fun getShipment(id: Int): Response<Shipment> {
    return RetrofitInstance.getApi.getShipment(id)
  }

  suspend fun postShipment(shipment: Shipment): Response<Shipment> {
    return RetrofitInstance.postApi.postShipment(shipment)
  }

  suspend fun putShipment(id: Int, shipment: Shipment): Response<Shipment> {
    return RetrofitInstance.putApi.putShipment(id, shipment)
  }

  suspend fun deleteShipment(id: Int): Response<Shipment> {
    return RetrofitInstance.deleteApi.deleteShipment(id)
  }

  /**
   * Tracking
   */
  suspend fun getTrackings(): Response<List<Tracking>> {
    return RetrofitInstance.getApi.getTrackings()
  }

  suspend fun getTracking(id: Int): Response<Tracking> {
    return RetrofitInstance.getApi.getTracking(id)
  }

  suspend fun postTracking(tracking: Tracking): Response<Tracking> {
    return RetrofitInstance.postApi.postTracking(tracking)
  }

  suspend fun putTracking(id: Int, tracking: Tracking): Response<Tracking> {
    return RetrofitInstance.putApi.putTracking(id, tracking)
  }

  suspend fun deleteTracking(id: Int): Response<Tracking> {
    return RetrofitInstance.deleteApi.deleteTracking(id)
  }

  /**
   * Truck
   */
  suspend fun getTrucks(): Response<List<Truck>> {
    return RetrofitInstance.getApi.getTrucks()
  }

  suspend fun getTruck(id: Int): Response<Truck> {
    return RetrofitInstance.getApi.getTruck(id)
  }

  suspend fun postTruck(truck: Truck): Response<Truck> {
    return RetrofitInstance.postApi.postTruck(truck)
  }

  suspend fun putTruck(id: Int, truck: Truck): Response<Truck> {
    return RetrofitInstance.putApi.putTruck(id, truck)
  }

  suspend fun deleteTruck(id: Int): Response<Truck> {
    return RetrofitInstance.deleteApi.deleteTruck(id)
  }
}