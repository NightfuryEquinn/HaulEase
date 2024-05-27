package com.example.haulease.viewmodels.admin

import androidx.lifecycle.ViewModel
import com.example.haulease.models.Sessions

class AdminProfileVM: ViewModel() {
  // Clear sessions and log out
  fun logoutAdmin() {
    Sessions.sessionToken = null
    Sessions.sessionEmail = null
    Sessions.sessionRole = null
  }
}