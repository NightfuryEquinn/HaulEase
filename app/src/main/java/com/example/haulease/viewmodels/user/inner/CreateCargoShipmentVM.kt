package com.example.haulease.viewmodels.user.inner

import android.location.Address
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.haulease.api.Repository
import com.example.haulease.models.Cargo
import com.example.haulease.models.Payment
import com.example.haulease.models.SampleTruck
import com.example.haulease.models.Sessions
import com.example.haulease.models.Shipment
import com.example.haulease.models.TempShipmentCargo
import com.example.haulease.models.Tracking
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.util.UUID


class CreateCargoShipmentVM: ViewModel() {
  private val repository: Repository = Repository()

  private var imageUrl: String = ""
  private var newPaymentId: Int = 0
  private var newTrackingId: Int = 0
  private var newTruckId: Int = 0

  // Temp shipment cargo
  val tempShipmentCargo: TempShipmentCargo = TempShipmentCargo(
    id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
    cargoList = mutableListOf()
  )

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

    if (res.isSuccessful) {
      res.body()?.let {
        newPaymentId = it.id
      }

      return newPaymentId
    }

    return 0
  }

  // Create tracking
  private suspend fun createTracking(theAddress: Address): Int {
    val newTracking = Tracking(
      id = 0,
      time = LocalDateTime.now().toString(),
      latitude = theAddress.latitude,
      longitude = theAddress.longitude
    )

    val res = repository.postTracking(newTracking)

    if (res.isSuccessful) {
      res.body()?.let {
        newTrackingId = it.id
      }

      return newTrackingId
    }

    return 0
  }

  // Create default truck driver
  private suspend fun createTruck(): Int {
    val newTruck = SampleTruck.SampleTrucks.random()

    val res = repository.postTruck(newTruck)

    if (res.isSuccessful) {
      res.body()?.let {
        newTruckId = it.id
      }

      return newTruckId
    }

    return 0
  }

  // Create shipment
  suspend fun placeShipment(
    shipmentDetail: Shipment,
    tempShipmentCargo: TempShipmentCargo,
    theAddress: Address,
    context: android.content.Context
  ) {
    if (Sessions.sessionToken != null &&
      createPayment() != 0 &&
      createTracking(theAddress) != 0 &&
      createTruck() != 0
    ) {
      // Get the shipment ID
      var theShipmentId: Int = 0

      shipmentDetail.paymentId = newPaymentId
      shipmentDetail.trackingId = newTrackingId
      shipmentDetail.truckId = newTruckId

      val shipRes = repository.postShipment(shipmentDetail)

      if (shipRes.isSuccessful) {
        shipRes.body()?.let {
          theShipmentId = it.id
        }

        for (cargos in tempShipmentCargo.cargoList) {
          cargos.shipmentId = theShipmentId
          imageUrl = uploadCargoImage(Uri.parse(cargos.image), context)
          cargos.image = imageUrl

          repository.postCargo(cargos)
        }

        Toast.makeText(context, "Shipment and cargo placed.", Toast.LENGTH_LONG).show()
      } else {
        Toast.makeText(context, "Failed to place shipment.", Toast.LENGTH_LONG).show()
      }
    } else {
      Toast.makeText(context, "Failed to create shipment.", Toast.LENGTH_LONG).show()
    }
  }

  // Append cargo to temp shipment cargo
  fun appendCargo(newCargo: Cargo) {
    tempShipmentCargo.cargoList.add(newCargo)
  }

  // Remove cargo from temp shipment cargo
  fun removeCargo(cargo: Cargo) {
    tempShipmentCargo.cargoList.remove(cargo)
  }
}