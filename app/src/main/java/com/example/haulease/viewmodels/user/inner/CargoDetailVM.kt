package com.example.haulease.viewmodels.user.inner

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Cargo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class CargoState {
  data object INITIAL : CargoState()
  data object LOADING : CargoState()
  data object SUCCESS : CargoState()
}

class CargoDetailVM: ViewModel() {
  private val repository: Repository = Repository()

  private val _cargoState: MutableStateFlow<CargoState> = MutableStateFlow(CargoState.INITIAL)

  var theCargoDetail: Cargo? = null

  // Set observer value
  val cargoState: MutableStateFlow<CargoState> = _cargoState

  // Get cargo detail
  private suspend fun getCargoDetail(
    theCargoId: Int
  ): Boolean {
    val res = repository.getCargo(theCargoId)

    res.body()?.let {
      if (res.isSuccessful) {
        theCargoDetail = it
      }

      return res.isSuccessful
    }

    return false
  }

  // Load cargo detail
  fun loadCargoDetail(
    theCargoId: Int,
    context: android.content.Context
  ) {
    _cargoState.value = CargoState.LOADING

    viewModelScope.launch {
      if (getCargoDetail(theCargoId)) {
        _cargoState.value = CargoState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get cargo detail", Toast.LENGTH_SHORT).show()
        _cargoState.value = CargoState.INITIAL
      }
    }
  }

  // Clear cargo details
  fun clearCargoDetail() {
    theCargoDetail = null
  }
}