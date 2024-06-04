package com.example.haulease.viewmodels.admin

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Sessions
import com.example.haulease.models.Shipment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Admin history status
sealed class AdminHistoryState {
  data object INITIAL: AdminHistoryState()
  data object LOADING: AdminHistoryState()
  data object SUCCESS: AdminHistoryState()
}

class AdminHistoryVM: ViewModel() {
  private val repository: Repository = Repository()

  private var adminSessionRole: String? = Sessions.sessionRole
  private var _adminHistoryState = MutableStateFlow<AdminHistoryState>(AdminHistoryState.INITIAL)

  var allShipmentsHistory: MutableList<Shipment> = mutableListOf()

  // Set observer value
  val adminHistoryState: StateFlow<AdminHistoryState> = _adminHistoryState

  // Get all past shipment orders
  private suspend fun getAllShipmentsHistory(): Boolean {
    val res = repository.getShipments()

    res.body()?.let { shipments ->
      if (res.isSuccessful && adminSessionRole == "Admin") {
        for (shipment in shipments) {
          if (shipment.status.startsWith("Completed")) {
            allShipmentsHistory.add(shipment)
          }
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Load all past shipment orders
  fun loadAllShipmentsHistory(
    context: android.content.Context
  ): List<Shipment> {
    _adminHistoryState.value = AdminHistoryState.LOADING

    viewModelScope.launch {
      if (getAllShipmentsHistory()) {
        _adminHistoryState.value = AdminHistoryState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get all past shipments.", Toast.LENGTH_LONG).show()
        _adminHistoryState.value = AdminHistoryState.INITIAL
      }
    }

    return allShipmentsHistory
  }

  // Clear past shipment orders
  fun clearAllShipmentsHistory() {
    allShipmentsHistory.clear()
  }
}