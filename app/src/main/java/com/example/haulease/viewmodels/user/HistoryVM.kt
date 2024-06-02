package com.example.haulease.viewmodels.user

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Sessions
import com.example.haulease.models.Shipment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// History status
sealed class HistoryState {
  data object INITIAL: HistoryState()
  data object LOADING: HistoryState()
  data object SUCCESS: HistoryState()
}

class HistoryVM: ViewModel() {
  private val repository: Repository = Repository()

  private var consignorSessionId: Int = Sessions.sessionToken?.toInt()!!
  private val _historyState = MutableStateFlow<HistoryState>(HistoryState.INITIAL)

  var theShipmentsHistory: MutableList<Shipment> = mutableListOf()

  // Set observer value
  val historyState: StateFlow<HistoryState> = _historyState

  // Get past shipment orders
  private suspend fun getShipmentsHistory(): Boolean {
    val res = repository.getShipmentsByConsignor(consignorSessionId)

    res.body()?.let { shipments ->
      if (res.isSuccessful) {
        for (shipment in shipments) {
          if (shipment.status.startsWith("Completed")) {
            theShipmentsHistory.add(shipment)
          }
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Load past shipment orders
  fun loadShipmentsHistory(
    context: android.content.Context
  ) {
    _historyState.value = HistoryState.LOADING

    viewModelScope.launch {
      if (getShipmentsHistory()) {
        _historyState.value = HistoryState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get your past shipments.", Toast.LENGTH_LONG).show()
        _historyState.value = HistoryState.INITIAL
      }
    }
  }

  // Clear past shipment orders
  fun clearShipmentsHistory() {
    theShipmentsHistory.clear()
  }
}