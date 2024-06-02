package com.example.haulease.viewmodels.admin.inner

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Cargo
import com.example.haulease.models.Sessions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AdminEditState {
  data object INITIAL: AdminEditState()
  data object LOADING: AdminEditState()
  data object SUCCESS: AdminEditState()
}

class AdminEditCargoVM: ViewModel() {
  private val repository: Repository = Repository()

  private var adminSessionRole: String? = Sessions.sessionRole
  private val _adminEditState = MutableStateFlow<AdminEditState>(AdminEditState.INITIAL)

  // Set observer value
  val adminEditState: StateFlow<AdminEditState> = _adminEditState

  // Update cargo details
  suspend fun updateCargoDetail(
    updatedCargo: Cargo?,
    context: android.content.Context
  ) {
    viewModelScope.launch {
      if (updatedCargo != null && adminSessionRole == "Admin") {
        val res = repository.putCargo(updatedCargo.id, updatedCargo)

        if (res.code() == 200) {
          Toast.makeText(context, "Cargo Updated", Toast.LENGTH_SHORT).show()
        } else {
          Toast.makeText(context, "Failed to Update Cargo", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}