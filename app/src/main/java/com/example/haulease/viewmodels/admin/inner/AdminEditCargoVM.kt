package com.example.haulease.viewmodels.admin.inner

import androidx.lifecycle.ViewModel
import com.example.haulease.api.Repository
import com.example.haulease.models.Sessions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
  suspend fun updateCargoDetail() {

  }
}