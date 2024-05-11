package com.example.haulease.viewmodels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Consignor
import com.example.haulease.models.Sessions
import kotlinx.coroutines.launch

class ProfileVM: ViewModel() {
  private val repository: Repository = Repository()

  // Clear sessions and log out
  fun logoutConsignor() {
    Sessions.sessionToken = null
    Sessions.sessionEmail = null
    Sessions.sessionRole = null
  }

  // Fetch details of consignor
  fun getConsignorProfile(): Consignor? {
    var theUserProfile: Consignor? = null

    viewModelScope.launch {
      val res = repository.getConsignor(Sessions.sessionToken?.toInt()!!)

      res.body()?.let {
        theUserProfile = it
      }
    }

    return theUserProfile
  }
}