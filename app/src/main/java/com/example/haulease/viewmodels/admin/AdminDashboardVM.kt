package com.example.haulease.viewmodels.admin

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Sessions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AdminDashboardState {
  data object INITIAL: AdminDashboardState()
  data object LOADING: AdminDashboardState()
  data object SUCCESS: AdminDashboardState()
}

class AdminDashboardVM: ViewModel() {
  private val repository: Repository = Repository()

  private var adminSessionRole: String? = Sessions.sessionRole
  private val _adminDashboardState = MutableStateFlow<AdminDashboardState>(AdminDashboardState.INITIAL)

  var shipmentsReceived: Int = 0
  var cargosTransported: Int = 0
  var weightShipped: Double = 0.0
  var totalIncome: Double = 0.0
  var totalActiveUsers: Int = 0
  var totalShipmentsDone: Int = 0

  // Set observer value
  val adminDashboardState: StateFlow<AdminDashboardState> = _adminDashboardState

  // Get total shipment received
  private suspend fun getShipmentsReceived(): Boolean {
    val res = repository.getShipments()

    res.body()?.let { shipments ->
      if (res.isSuccessful && adminSessionRole == "Admin") {
        shipmentsReceived = shipments.size

        // Get completed shipment
        for (shipment in shipments) {
          if (shipment.status.startsWith("Completed")) {
            totalShipmentsDone++
          }
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Get total cargo transported
  private suspend fun getCargosTransported(): Boolean {
    val res = repository.getCargos()

    res.body()?.let { cargos ->
      if (res.isSuccessful && adminSessionRole == "Admin") {
        cargosTransported = cargos.size

        // Get weight shipped
        for (cargo in cargos) {
          weightShipped += cargo.weight
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Get total income
  private suspend fun getTotalIncome(): Boolean {
    val res = repository.getPayments()

    res.body()?.let { payments ->
      if (res.isSuccessful && adminSessionRole == "Admin") {
        for (payment in payments) {
          val total = payment.first?.plus(payment.second!!)?.plus(payment.final!!) ?: 0.0
          totalIncome += total
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Get total consignor registered
  private suspend fun getTotalActiveUsers(): Boolean {
    val res = repository.getConsignors()

    res.body()?.let {
      if (res.isSuccessful && adminSessionRole == "Admin") {
        totalActiveUsers = it.size
      }

      return res.isSuccessful
    }

    return false
  }

  // Get data analysis of whole database
  fun getAnalysis(
    context: android.content.Context
  ) {
    _adminDashboardState.value = AdminDashboardState.LOADING

    viewModelScope.launch {
      if (getShipmentsReceived() && getCargosTransported() && getTotalIncome() && getTotalActiveUsers()) {
        _adminDashboardState.value = AdminDashboardState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to fetch data. Please reload.", Toast.LENGTH_LONG).show()
        _adminDashboardState.value = AdminDashboardState.INITIAL
      }
    }
  }
}