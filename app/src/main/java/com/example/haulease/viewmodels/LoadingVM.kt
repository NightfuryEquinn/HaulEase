package com.example.haulease.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoadingVM: ViewModel() {
  val loadingProgress = MutableStateFlow(0.0f)

  init {
    startLoadingProgress()
  }

  private fun startLoadingProgress() {
    viewModelScope.launch {
      for (progress in 0..100) {
        loadingProgress.value = progress / 100.0f
        delay(50)
      }
    }
  }
}