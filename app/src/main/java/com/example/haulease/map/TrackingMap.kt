package com.example.haulease.map

import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun TrackingMap() {
  var map: GoogleMap? by remember { mutableStateOf(null) }
  val context = LocalContext.current
  var shipmentMarker: Marker? by remember { mutableStateOf(null) }
  var userAddress by remember { mutableStateOf("") }
  var actualAddress by remember { mutableStateOf<Address?>(null) }
  val focusManager = LocalFocusManager.current
  val random = Random()

  fun updateShipmentLocation() {
    val latitude = 2.9975 + random.nextDouble() * (3.2975 - 2.9975)
    val longitude = 101.3749 + random.nextDouble() * (101.7349 - 101.3749)
    val newLocation = LatLng(latitude, longitude)
    shipmentMarker?.position = newLocation
    map?.animateCamera(CameraUpdateFactory.newLatLng(newLocation))
  }

  fun addMarkers(latLng: LatLng) {
    // Remove shipment marker
    shipmentMarker?.remove()

    map?.let {
      val origin = LatLng(3.1390, 101.6869)
      val destination = LatLng(39.9042, 116.4074)

      it.addMarker(MarkerOptions().position(origin).title("Origin"))
      it.addMarker(MarkerOptions().position(destination).title("Destination"))

      shipmentMarker = it.addMarker(MarkerOptions().position(latLng).title("Shipment"))
    }

    map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
  }

  fun getActualAddress(userAddresses: String) {
    GlobalScope.launch(Dispatchers.Main) {
      val geocoder = Geocoder(context)
      val result = userAddresses.let {
        geocoder.getFromLocationName(it, 1)
      }

      if (!result.isNullOrEmpty()) {
        val addressArray: List<Address> = result

        userAddress = addressArray[0].getAddressLine(0)
        actualAddress = addressArray[0]

        val latLng = LatLng(addressArray[0].latitude, addressArray[0].longitude)
        addMarkers(latLng)
      } else {
        Toast.makeText(context, "Address not found", Toast.LENGTH_SHORT).show()
      }
    }
  }

  fun checkLocation() {
    if (userAddress.isNotBlank()) {
      getActualAddress(userAddress)
    } else {
      Toast.makeText(context, "Please enter an address", Toast.LENGTH_SHORT).show()
    }
  }

  AndroidView(
    modifier = Modifier.fillMaxSize(),
    factory = { ctx ->
      MapView(ctx).apply {
        onCreate(null)
        getMapAsync { googleMap ->
          map = googleMap
          map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(4.2105, 101.9758), 10f))
        }
      }
    },
    update = { mapView ->
      mapView.getMapAsync { googleMap ->
        map = googleMap
      }
    }
  )

  Column(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top
  ) {
    TextField(
      value = userAddress,
      onValueChange = { userAddress = it },
      label = { Text("Enter Address") },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
      ),
      keyboardActions = KeyboardActions(
        onDone = { focusManager.clearFocus() }
      ),
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = { checkLocation() }) {
      Text("Check Location")
    }
  }

  // Update every 30 minutes
  LaunchedEffect(true) {
    while (true) {
      updateShipmentLocation()
      delay(1 * 60 * 1000)
    }
  }
}