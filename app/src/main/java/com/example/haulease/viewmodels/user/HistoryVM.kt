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
  private var theShipmentsHistory: MutableList<Shipment> = mutableListOf()
  private val _historyState = MutableStateFlow<HistoryState>(HistoryState.INITIAL)

  // Set observer value
  val historyState: StateFlow<HistoryState> = _historyState

  // Get past shipment orders
  private suspend fun getShipmentsHistory(): Boolean {
    val res = repository.getShipmentByConsignor(consignorSessionId)

    res.body()?.let {
      if (res.isSuccessful) {
        theShipmentsHistory = it.toMutableList()
      }

      return res.isSuccessful
    }

    return false
  }

  fun loadShipmentsHistory(
    context: android.content.Context
  ): List<Shipment> {
    _historyState.value = HistoryState.LOADING

    viewModelScope.launch {
      if (getShipmentsHistory()) {
        _historyState.value = HistoryState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get your past shipments.", Toast.LENGTH_LONG).show()
        _historyState.value = HistoryState.INITIAL
      }
    }

    return theShipmentsHistory
  }

  fun clearShipmentsHistory() {
    theShipmentsHistory.clear()
  }
}