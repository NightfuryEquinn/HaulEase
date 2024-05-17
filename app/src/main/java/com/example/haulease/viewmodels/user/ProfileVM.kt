package com.example.haulease.viewmodels.user

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Consignor
import com.example.haulease.models.Sessions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class ProfileState {
  data object INITIAL: ProfileState()
  data object LOADING: ProfileState()
  data object SUCCESS: ProfileState()
}

class ProfileVM: ViewModel() {
  private val repository: Repository = Repository()

  private var consignorSessionId: Int = Sessions.sessionToken?.toInt()!!
  private val _profileState = MutableStateFlow<ProfileState>(ProfileState.INITIAL)

  var theUserProfile: Consignor? = null

  // Set observer value
  val profileState: MutableStateFlow<ProfileState> = _profileState

  // Fetch details of consignor
  private suspend fun getConsignorProfile(): Boolean {
    val res = repository.getConsignor(consignorSessionId)

    res.body()?.let {
      if (res.isSuccessful) {
        theUserProfile = it
      }

      return res.isSuccessful
    }

    return false
  }

  // Load details of consignor
  fun loadConsignorProfile(
    context: android.content.Context
  ): Consignor? {
    _profileState.value = ProfileState.LOADING

    viewModelScope.launch {
      if (getConsignorProfile()) {
        _profileState.value = ProfileState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to load profile.", Toast.LENGTH_SHORT).show()
        _profileState.value = ProfileState.INITIAL
      }
    }

    return theUserProfile
  }

  // Clear sessions and log out
  fun logoutConsignor() {
    Sessions.sessionToken = null
    Sessions.sessionEmail = null
    Sessions.sessionRole = null
  }

  // Clear details of consignor
  fun clearConsignorProfile() {
    theUserProfile = null
  }
}