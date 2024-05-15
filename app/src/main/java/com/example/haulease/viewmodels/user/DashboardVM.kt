package com.example.haulease.viewmodels.user

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Sessions
import com.example.haulease.models.Shipment
import com.example.haulease.models.ShipmentPayment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DashboardState {
  data object INITIAL: DashboardState()
  data object LOADING: DashboardState()
  data object SUCCESS: DashboardState()
}

class DashboardVM: ViewModel() {
  private val repository: Repository = Repository()

  private var consignorSessionId: Int = Sessions.sessionToken?.toInt()!!
  private val _dashboardState = MutableStateFlow<DashboardState>(DashboardState.INITIAL)

  var latestShipment: Shipment? = null
  var latestUnpaidShipment: ShipmentPayment? = null
  var totalShipments: Int = 0;
  var totalCargo: Int = 0;
  var totalWeight: Double = 0.0;
  var totalSpend: Double = 0.0;

  // Set observer value
  val dashboardState: StateFlow<DashboardState> = _dashboardState

  // Get the details of latest shipment
  private suspend fun getLatestShipments(): Boolean {
    val res = repository.getShipmentsByConsignor(consignorSessionId)

    res.body()?.let {
      if (res.isSuccessful) {
        latestShipment = it.last()
        // Count total shipment of the consignor
        totalShipments = it.size
      }

      return res.isSuccessful
    }

    return false
  }

  // Get the details of latest unpaid shipment
  private suspend fun getLatestUnpaidShipments(): Boolean {
    val res = repository.getShipmentPayment(consignorSessionId)

    res.body()?.let {
      if (res.isSuccessful) {
        for (sp in it.reversed()) {
          if (sp.payment.first == 0.0 || sp.payment.second == 0.0 || sp.payment.final == 0.0) {
            latestUnpaidShipment = sp
            break
          }
        }

        // Get total spent
        for (sp in it) {
          if (sp.payment.first != null && sp.payment.second != null && sp.payment.final != null) {
            val eachSpend = sp.payment.first?.plus(sp.payment.second!!)?.plus(sp.payment.final!!)
            totalSpend += eachSpend ?: 0.0
          }
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Get the total cargos
  private suspend fun getTotalCargos(): Boolean {
    val shipRes = repository.getShipmentsByConsignor(consignorSessionId)

    shipRes.body()?.let { shipments ->
      if (shipRes.isSuccessful) {
        for (shipment in shipments) {
          val cargoRes = repository.getCargosByShipment(shipment.id)

          cargoRes.body()?.let { cargos ->
            if (shipRes.isSuccessful && cargoRes.isSuccessful) {
              totalCargo += cargos.size

              // Get the total weight of cargos
              for (cargo in cargos) {
                totalWeight += cargo.weight
              }
            }
          }
        }
      }

      return shipRes.isSuccessful
    }

    return false
  }

  // Get data analysis of logged in consignor
  fun getAnalysis(
    context: android.content.Context
  ) {
    _dashboardState.value = DashboardState.LOADING

    viewModelScope.launch {
      if (getLatestShipments() && getLatestUnpaidShipments() && getTotalCargos()) {
        _dashboardState.value = DashboardState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to fetch data. Please reload.", Toast.LENGTH_LONG).show()
        _dashboardState.value = DashboardState.INITIAL
      }
    }
  }

  // Clear all shipments
  fun clearShipments() {
    latestShipment = null
    latestUnpaidShipment = null
  }
}