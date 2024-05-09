package com.example.haulease.viewmodels

import androidx.lifecycle.ViewModel
import com.example.haulease.api.Repository
import com.google.firebase.auth.FirebaseAuth

class RegisterVM: ViewModel() {
  private val fireAuth = FirebaseAuth.getInstance()
  private val repository: Repository = Repository()


}