package com.example.haulease.viewmodels.user.inner

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haulease.api.Repository
import com.example.haulease.models.Payment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class PaymentState {
  data object INITIAL : PaymentState()
  data object LOADING : PaymentState()
  data object SUCCESS : PaymentState()
}

class PaymentVM: ViewModel() {
  private val repository: Repository = Repository()

  private val _paymentState: MutableStateFlow<PaymentState> = MutableStateFlow(PaymentState.INITIAL)

  var thePaymentDetail: Payment? = null

  // Set observer value
  val paymentState: MutableStateFlow<PaymentState> = _paymentState

  // Get payment details
  private suspend fun getPaymentDetails(thePaymentId: Int): Boolean {
    if (thePaymentId != 0) {
      val res = repository.getPayment(thePaymentId)

      res.body()?.let {
        if (res.isSuccessful) {
          thePaymentDetail = it
        }

        return res.isSuccessful
      }
    }

    return false
  }

  // Load payment details
  fun loadPaymentDetails(
    thePaymentId: Int,
    context: android.content.Context
  ) {
    _paymentState.value = PaymentState.LOADING

    viewModelScope.launch {
      if (getPaymentDetails(thePaymentId)) {
        _paymentState.value = PaymentState.SUCCESS
      } else {
        Toast.makeText(context, "Failed to get payment detail", Toast.LENGTH_LONG).show()
        _paymentState.value = PaymentState.INITIAL
      }
    }
  }

  // Clear payment details
  fun clearPaymentDetail() {
    thePaymentDetail = null
  }

  // Calculate total cargo price

  // Make payment request
  fun makePaymentRequest(
    thePaymentId: Int,
    totalPayable: Double,
    context: android.content.Context
  ) {

  }
}