package com.example.haulease.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.delay
import java.util.Random

@Composable
fun TrackingMap() {
  var map: GoogleMap? by remember { mutableStateOf(null) }
  val context = LocalContext.current
  var shipmentMarker: Marker? by remember { mutableStateOf(null) }
  val coroutineScope = rememberCoroutineScope()
  val random = Random()

  fun updateShipmentLocation() {
    val latitude = 2.9975 + random.nextDouble() * (3.2975 - 2.9975) // Random latitude within Malaysia
    val longitude = 101.3749 + random.nextDouble() * (101.7349 - 101.3749) // Random longitude within Malaysia
    val newLocation = LatLng(latitude, longitude)
    shipmentMarker?.position = newLocation
    map?.animateCamera(CameraUpdateFactory.newLatLng(newLocation))
  }

  fun addMarkers() {
    // Remove shipment marker
    shipmentMarker?.remove()

    map?.let {
      val origin = LatLng(3.1390, 101.6869) // Kuala Lumpur, Malaysia
      val destination = LatLng(39.9042, 116.4074) // Beijing, China

      it.addMarker(MarkerOptions().position(origin).title("Origin"))
      it.addMarker(MarkerOptions().position(destination).title("Destination"))

      val shipmentLocation = LatLng(3.0000, 101.4500) // Klang Harbor, Malaysia (initial location)
      shipmentMarker = it.addMarker(MarkerOptions().position(shipmentLocation).title("Shipment"))
    }
  }

  AndroidView(
    modifier = Modifier.fillMaxSize(),
    factory = { context ->
      MapView(context).apply {
        onCreate(null)
        getMapAsync { googleMap ->
          map = googleMap
          map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(4.2105, 101.9758), 5f))
          addMarkers()
        }
      }
    },
    update = { mapView ->
      mapView.getMapAsync { googleMap ->
        map = googleMap
        addMarkers()
      }
    }
  )

  LaunchedEffect(true) {
    while (true) {
      updateShipmentLocation()
      delay(1 * 60 * 1000) // Update every 30 minutes
    }
  }
}