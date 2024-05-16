package com.example.haulease.viewmodels.user.inner

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.haulease.api.Repository
import com.example.haulease.models.Cargo
import com.example.haulease.models.Payment
import com.example.haulease.models.TempShipmentCargo
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID


class CreateCargoShipmentVM: ViewModel() {
  private val repository: Repository = Repository()

  private var imageUrl: String = ""

  val tempShipmentCargo: TempShipmentCargo = createTempShipmentCargo()

  // Upload image to Firebase Storage
  private suspend fun uploadCargoImage(
    image: Uri,
    context: android.content.Context
  ): String {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference.child("images/cargo/${UUID.randomUUID()}.jpg")
    val uploadTask = storageRef.putFile(image)

    val downloadUrl = try {
      uploadTask.await()
      storageRef.downloadUrl.await().toString()
    } catch (e: Exception) {
      Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
      return ""
    }

    return downloadUrl
  }

  // Create payment first, second, final default as 0.0
  private suspend fun createPayment(): Int {
    val newPayment = Payment(
      id = 0,
      first = 0.0,
      second = 0.0,
      final = 0.0
    )

    val res = repository.postPayment(newPayment)
    var newPaymentId = 0

    if (res.isSuccessful) {
      res.body()?.let {
        newPaymentId = it.id
      }

      return newPaymentId
    }

    return 0
  }

  // Create tracking


  // Create default truck driver


  // TODO Create shipment
  fun placeShipment(
    tempShipmentCargo: TempShipmentCargo
  ) {

  }

  // Append cargo to temp shipment cargo
  fun appendCargo(newCargo: Cargo) {
    tempShipmentCargo.cargoList.add(newCargo)
  }

  // Remove cargo from temp shipment cargo
  fun removeCargo(cargo: Cargo) {
    tempShipmentCargo.cargoList.remove(cargo)
  }

  // Create temp shipment cargo
  private fun createTempShipmentCargo(): TempShipmentCargo {
    val tempShipmentCargo = TempShipmentCargo(
      id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
      cargoList = mutableListOf()
    )

    return tempShipmentCargo
  }
}