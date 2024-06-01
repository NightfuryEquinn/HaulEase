package com.example.haulease.viewmodels.user.inner

import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Cargo
import com.example.haulease.models.Sessions
import com.example.haulease.models.Shipment
import com.example.haulease.models.ShipmentPayment
import com.example.haulease.models.ShipmentTracking
import com.example.haulease.models.ShipmentTruck
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Shipment detail status
sealed class ShipmentDetailState {
  data object INITIAL: ShipmentDetailState()
  data object LOADING: ShipmentDetailState()
  data object SUCCESS: ShipmentDetailState()
}

class ShipmentDetailVM: ViewModel() {
  private val repository: Repository = Repository()

  private var consignorSessionId: Int = Sessions.sessionToken?.toInt()!!
  private val _shipmentDetailState = MutableStateFlow<ShipmentDetailState>(ShipmentDetailState.INITIAL)

  var theShipmentDetail: ShipmentPayment? = null
  var theShipmentTracking: ShipmentTracking? = null
  var theShipmentTruck: ShipmentTruck? = null
  var theShipmentCargos: List<Cargo> = emptyList()

  // Set observer value
  val shipmentDetailState: StateFlow<ShipmentDetailState> = _shipmentDetailState

  // Get shipment cargo list
  private suspend fun getShipmentCargo(theShipmentId: Int): Boolean {
    if (theShipmentId != 0) {
      val res = repository.getCargosByShipment(theShipmentId)

      res.body()?.let {
        if (res.isSuccessful) {
          theShipmentCargos = it
        }

        return res.isSuccessful
      }
    }

    return false
  }

  // Get shipment detail
  private suspend fun getShipmentDetail(theShipmentId: Int): Boolean {
    val res = repository.getShipmentPayment(consignorSessionId)

    res.body()?.let { shipmentPayments ->
      if (res.isSuccessful && theShipmentId != 0) {
        for (shipmentPayment in shipmentPayments) {
          if (shipmentPayment.shipment.id == theShipmentId) {
            theShipmentDetail = shipmentPayment
          }
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Get shipment tracking
  private suspend fun getShipmentTracking(theShipmentId: Int, theConsignorId: Int): Boolean {
    val res = repository.getShipmentTracking(theConsignorId)

    res.body()?.let { shipmentTrackings ->
      if (res.isSuccessful && theShipmentId != 0 && theConsignorId != 0) {
        for (shipmentTracking in shipmentTrackings) {
          if (shipmentTracking.shipment.consignorId == theConsignorId && shipmentTracking.shipment.id == theShipmentId) {
            theShipmentTracking = shipmentTracking
          }
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Get shipment assigned driver
  private suspend fun getShipmentTruck(theShipmentId: Int, theConsignorId: Int): Boolean {
    val res = repository.getShipmentTruck(theConsignorId)

    res.body()?.let { shipmentTrucks ->
      if (res.isSuccessful && theShipmentId != 0 && theConsignorId != 0) {
        for (shipmentTruck in shipmentTrucks) {
          if (shipmentTruck.shipment.consignorId == theConsignorId && shipmentTruck.shipment.id == theShipmentId) {
            theShipmentTruck = shipmentTruck
          }
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Confirm shipment delivered
  suspend fun confirmShipment(
    theShipment: Shipment,
    context: android.content.Context
  ) {
    theShipment.status = "Completed at ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}"

    val res = repository.putShipment(theShipment.id, theShipment)

    if (res.code() == 200) {
      Toast.makeText(context, "Confirm shipment delivered.", Toast.LENGTH_SHORT).show()
    } else {
      Toast.makeText(context, "Failed to confirm shipment.", Toast.LENGTH_SHORT).show()
    }
  }

  // Check shipment detail available
  fun checkShipmentDetail(
    theShipmentId: Int,
    context: android.content.Context
  ) {
    _shipmentDetailState.value = ShipmentDetailState.LOADING

    viewModelScope.launch {
      if (getShipmentDetail(theShipmentId) &&
        getShipmentTracking(theShipmentId, consignorSessionId) &&
        getShipmentTruck(theShipmentId, consignorSessionId) &&
        getShipmentCargo(theShipmentId)
      ) {
        _shipmentDetailState.value = ShipmentDetailState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get shipment detail", Toast.LENGTH_LONG).show()
        _shipmentDetailState.value = ShipmentDetailState.INITIAL
      }
    }
  }

  // Clear shipment detail
  fun clearShipmentDetail() {
    theShipmentDetail = null
    theShipmentTracking = null
    theShipmentTruck = null
    theShipmentCargos = emptyList()
  }

  // Convert string to address
  fun getAddressFromString(
    addressString: String,
    context: android.content.Context
  ): Address? {
    val geocoder = Geocoder(context)

    return try {
      val addressList = geocoder.getFromLocationName(addressString, 1)

      if (addressList!!.isNotEmpty()) {
        addressList[0]
      } else {
        null
      }
    } catch (e: Exception) {
      Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
      null
    }
  }
}