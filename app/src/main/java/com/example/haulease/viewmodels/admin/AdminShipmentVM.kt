package com.example.haulease.viewmodels.admin

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

sealed class AdminShipmentState {
  data object INITIAL: AdminShipmentState()
  data object LOADING: AdminShipmentState()
  data object SUCCESS: AdminShipmentState()
}

class AdminShipmentVM: ViewModel() {
  private val repository: Repository = Repository()

  private var adminSessionRole: String? = Sessions.sessionRole
  private var theShipments: List<Shipment> = emptyList()
  private val _adminShipmentState = MutableStateFlow<AdminShipmentState>(AdminShipmentState.INITIAL)

  var pickupShipment: MutableList<Shipment> = mutableListOf()
  var harbourShipment: MutableList<Shipment> = mutableListOf()
  var enrouteShipment: MutableList<Shipment> = mutableListOf()
  var arrivedShipment: MutableList<Shipment> = mutableListOf()

  // Set observer value
  val adminShipmentState: StateFlow<AdminShipmentState> = _adminShipmentState

  // Categorize all shipments by status
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
  private suspend fun getAllShipments(): Boolean {
    val res = repository.getShipments()

    res.body()?.let {
      if (res.isSuccessful && adminSessionRole == "Admin") {
        theShipments = it
        categoriseShipment()
      }

      return res.isSuccessful
    }

    return false
  }

  // Load all shipments for admin
  fun loadAllShipments(
    context: android.content.Context
  ) {
    _adminShipmentState.value = AdminShipmentState.LOADING

    viewModelScope.launch {
      if (getAllShipments()) {
        _adminShipmentState.value = AdminShipmentState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get your active shipments.", Toast.LENGTH_LONG).show()
        _adminShipmentState.value = AdminShipmentState.INITIAL
      }
    }
  }

  // Clear all shipments
  fun clearAllShipments() {
    pickupShipment.clear()
    harbourShipment.clear()
    enrouteShipment.clear()
    arrivedShipment.clear()
  }
}