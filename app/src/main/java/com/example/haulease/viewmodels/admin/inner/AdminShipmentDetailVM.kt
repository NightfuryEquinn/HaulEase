package com.example.haulease.viewmodels.admin.inner

import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Cargo
import com.example.haulease.models.Sessions
import com.example.haulease.models.ShipmentPayment
import com.example.haulease.models.ShipmentTracking
import com.example.haulease.models.ShipmentTruck
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Admin shipment detail status
sealed class AdminShipmentDetailState {
  data object INITIAL: AdminShipmentDetailState()
  data object LOADING: AdminShipmentDetailState()
  data object SUCCESS: AdminShipmentDetailState()
}

class AdminShipmentDetailVM: ViewModel() {
  private val repository: Repository = Repository()

  private var adminSessionRole: String? = Sessions.sessionRole
  private val _adminShipmentDetailState = MutableStateFlow<AdminShipmentDetailState>(AdminShipmentDetailState.INITIAL)

  var theShipmentDetail: ShipmentPayment? = null
  var theShipmentTracking: ShipmentTracking? = null
  var theShipmentTruck: ShipmentTruck? = null
  var theShipmentCargos: List<Cargo> = emptyList()

  // Set observer value
  val adminShipmentDetailState: StateFlow<AdminShipmentDetailState> = _adminShipmentDetailState

  // Get shipment cargo list
  private suspend fun getShipmentCargo(theShipmentId: Int): Boolean {
    if (theShipmentId != 0) {
      val res = repository.getCargosByShipment(theShipmentId)

      res.body()?.let {
        if (res.isSuccessful && adminSessionRole == "Admin") {
          theShipmentCargos = it
        }

        return res.isSuccessful
      }
    }

    return false
  }

  // Get shipment detail
  private suspend fun getShipmentDetail(
    theShipmentId: Int,
    theConsignorId: Int
  ): Boolean {
    val res = repository.getShipmentPayment(theConsignorId)

    res.body()?.let { shipmentPayments ->
      if (res.isSuccessful && theShipmentId != 0 && theConsignorId != 0 && adminSessionRole == "Admin") {
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
      if (res.isSuccessful && theShipmentId != 0 && theConsignorId != 0 && adminSessionRole == "Admin") {
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
      if (res.isSuccessful && theShipmentId != 0 && theConsignorId != 0 && adminSessionRole == "Admin") {
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

  // Update shipment status
  suspend fun updateShipmentStatus(
    theShipmentId: Int,
    theStatus: String,
    context: android.content.Context
  ) {
    val updatedShipmentDetail = theShipmentDetail?.shipment
    updatedShipmentDetail?.status = theStatus

    if (updatedShipmentDetail != null) {
      val res = repository.putShipment(theShipmentId, updatedShipmentDetail)

      if (res.code() == 200) {
        Toast.makeText(context, "Shipment status updated.", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(context, "Failed to update shipment status.", Toast.LENGTH_SHORT).show()
      }
    } else {
      Toast.makeText(context, "Failed to fetch updated shipment detail.", Toast.LENGTH_LONG).show()
    }
  }

  // Check shipment detail available
  fun checkShipmentDetail(
    theShipmentId: Int,
    theConsignorId: Int,
    context: android.content.Context
  ) {
    _adminShipmentDetailState.value = AdminShipmentDetailState.LOADING

    viewModelScope.launch {
      if (getShipmentDetail(theShipmentId, theConsignorId) &&
        getShipmentTracking(theShipmentId, theConsignorId) &&
        getShipmentTruck(theShipmentId, theConsignorId) &&
        getShipmentCargo(theShipmentId)
      ) {
        _adminShipmentDetailState.value = AdminShipmentDetailState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get shipment detail", Toast.LENGTH_LONG).show()
        _adminShipmentDetailState.value = AdminShipmentDetailState.INITIAL
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