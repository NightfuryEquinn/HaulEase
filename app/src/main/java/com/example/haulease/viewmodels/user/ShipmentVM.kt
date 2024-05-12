package com.example.haulease.viewmodels.user

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Sessions
import com.example.haulease.models.Shipment
import com.example.haulease.validations.CargoStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ShipmentState {
  data object INITIAL: ShipmentState()
  data object LOADING: ShipmentState()
  data object SUCCESS: ShipmentState()
}

class ShipmentVM: ViewModel() {
  private val repository: Repository = Repository()

  private var consignorSessionId: Int = Sessions.sessionToken?.toInt()!!
  private var theShipments: List<Shipment> = emptyList()
  private val _shipmentState = MutableStateFlow<ShipmentState>(ShipmentState.INITIAL)

  var pickupShipment: MutableList<Shipment> = mutableListOf()
  var harbourShipment: MutableList<Shipment> = mutableListOf()
  var enrouteShipment: MutableList<Shipment> = mutableListOf()
  var arrivedShipment: MutableList<Shipment> = mutableListOf()

  // Set observer value
  val shipmentState: StateFlow<ShipmentState> = _shipmentState

  // Categorise shipments by status
  private fun categoriseShipment() {
    theShipments.forEach { shipment ->
      when (shipment.status) {
        CargoStatus.status1.titleText,
        CargoStatus.status2.titleText -> {
          pickupShipment.add(shipment)
        }
        CargoStatus.status3.titleText,
        CargoStatus.status4.titleText,
        CargoStatus.status5.titleText -> {
          harbourShipment.add(shipment)
        }
        CargoStatus.status6.titleText,
        CargoStatus.status7.titleText -> {
          enrouteShipment.add(shipment)
        }
        CargoStatus.status8.titleText,
        CargoStatus.status9.titleText -> {
          arrivedShipment.add(shipment)
        }
      }
    }
  }

  // Get active shipment orders
  private suspend fun getShipments(): Boolean {
    val res = repository.getShipmentByConsignor(consignorSessionId)

    res.body()?.let {
      if (res.isSuccessful) {
        theShipments = it
        categoriseShipment()
      }

      return res.isSuccessful
    }

    return false
  }

  fun loadShipments(
    context: android.content.Context
  ) {
    _shipmentState.value = ShipmentState.LOADING

    viewModelScope.launch {
      if (getShipments()) {
        _shipmentState.value = ShipmentState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get your active shipments.", Toast.LENGTH_LONG).show()
        _shipmentState.value = ShipmentState.INITIAL
      }
    }
  }

  fun clearShipments() {
    pickupShipment.clear()
    harbourShipment.clear()
    enrouteShipment.clear()
    arrivedShipment.clear()
  }
}