package com.example.haulease.viewmodels.admin.inner

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Cargo
import com.example.haulease.models.Sessions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class AdminCargoState {
  data object INITIAL: AdminCargoState()
  data object LOADING: AdminCargoState()
  data object SUCCESS: AdminCargoState()
}

class AdminCargoDetailVM: ViewModel() {
  private val repository: Repository = Repository()

  private var adminSessionRole: String? = Sessions.sessionRole
  private val _adminCargoState: MutableStateFlow<AdminCargoState> = MutableStateFlow(AdminCargoState.INITIAL)

  var theCargoDetail: Cargo? = null

  // Set observer value
  val adminCargoState: MutableStateFlow<AdminCargoState> = _adminCargoState

  // Get cargo detail
  private suspend fun getCargoDetail(
    theCargoId: Int
  ): Boolean {
    val res = repository.getCargo(theCargoId)

    res.body()?.let {
      if (res.isSuccessful && adminSessionRole == "Admin") {
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
    _adminCargoState.value = AdminCargoState.LOADING

    viewModelScope.launch {
      if (getCargoDetail(theCargoId)) {
        _adminCargoState.value = AdminCargoState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get cargo detail", Toast.LENGTH_SHORT).show()
        _adminCargoState.value = AdminCargoState.INITIAL
      }
    }
  }

  // Clear cargo details
  fun clearCargoDetail() {
    theCargoDetail = null
  }
}