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
  var totalCargoFees: Double = 0.0

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

  // Update payment detail
  private suspend fun updatePaymentDetail(
    thePaymentId: Int,
    updatedPaymentDetail: Payment?
  ): Boolean {
    if (updatedPaymentDetail != null) {
      val res = repository.putPayment(thePaymentId, updatedPaymentDetail)

      if (res.code() == 200) {
        return true
      }
    }

    return false
  }

  // Calculate total cargo price
  private suspend fun calculateCargoPrice(
    theShipmentId: Int,
  ): Boolean {
    val res = repository.getCargosByShipment(theShipmentId)

    res.body()?.let { cargos ->
      if (res.isSuccessful) {
        for (cargo in cargos) {
          val volWeight = cargo.height * cargo.width * cargo.length

          totalCargoFees += if (volWeight < cargo.weight) {
            cargo.weight
          } else {
            volWeight
          }
        }
      }

      return res.isSuccessful
    }

    return false
  }

  // Make payment request
  suspend fun makePaymentRequest(
    thePaymentId: Int,
    billingCount: Int,
    totalPayable: Double,
    context: android.content.Context
  ) {
    val updatedPaymentDetail: Payment? = thePaymentDetail

    if (totalPayable != 0.0) {
      when (billingCount) {
        1 -> {
          updatedPaymentDetail?.first = totalPayable
        }
        2 -> {
          updatedPaymentDetail?.second = totalPayable
        }
        3 -> {
          updatedPaymentDetail?.final = totalPayable
        }
      }

      if (updatePaymentDetail(thePaymentId, updatedPaymentDetail)) {
        Toast.makeText(context, "Payment successful", Toast.LENGTH_LONG).show()
      } else {
        Toast.makeText(context, "Failed to make payment", Toast.LENGTH_LONG).show()
      }
    }
  }

  // Load payment details
  fun loadPaymentDetails(
    theShipmentId: Int,
    thePaymentId: Int,
    context: android.content.Context
  ) {
    _paymentState.value = PaymentState.LOADING

    viewModelScope.launch {
      if (getPaymentDetails(thePaymentId) && calculateCargoPrice(theShipmentId)) {
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
    totalCargoFees = 0.0
  }
}