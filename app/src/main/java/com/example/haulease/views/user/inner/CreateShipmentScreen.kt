package com.example.haulease.views.user.inner

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.haulease.R
import com.example.haulease.models.Sessions
import com.example.haulease.models.Shipment
import com.example.haulease.navigations.routes.UserInnerRoutes
import com.example.haulease.navigations.routes.UserRoutes
import com.example.haulease.ui.components.SimpleCargoBox
import com.example.haulease.ui.components.SimpleTextField
import com.example.haulease.validations.CargoStatus
import com.example.haulease.viewmodels.user.inner.CreateCargoShipmentVM
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission", "StateFlowValueCalledInComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CreateShipmentScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit,
  createCargoShipmentVM: CreateCargoShipmentVM
) {
  val cScope = rememberCoroutineScope()
  val tempShipmentCargo = createCargoShipmentVM.tempShipmentCargo.value

  // Map variables
  val context = LocalContext.current
  val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

  var map: GoogleMap? by remember { mutableStateOf(null) }
  var oriMarker: Marker? by remember { mutableStateOf(null) }
  var destMarker: Marker? by remember { mutableStateOf(null) }

  val userOrigin = remember { mutableStateOf("") }
  val userDest = remember { mutableStateOf("") }

  var liveAddress by remember { mutableStateOf("") }
  var originAddress by remember { mutableStateOf<Address?>(null) }
  var destAddress by remember { mutableStateOf<Address?>(null) }

  // State variables
  val name = remember { mutableStateOf(tempShipmentCargo.receiverName) }
  val contact = remember { mutableStateOf(tempShipmentCargo.receiverContact) }
  val origin = remember { mutableStateOf(liveAddress) }
  val dest = remember { mutableStateOf("") }

  // Validations
  val allFieldsNotEmpty = name.value.isNotBlank()
      && contact.value.isNotBlank()
      && origin.value.isNotBlank()
      && dest.value.isNotBlank()
      && (tempShipmentCargo.cargoList.isNotEmpty())

  val originOrDest = origin.value.isNotBlank() && dest.value.isNotBlank()

  // Add markers on map
  fun addMarkers(latLng: LatLng? = null) {
    // Remove previous markers
    oriMarker?.remove()
    destMarker?.remove()

    map?.let {
      if (originAddress != null) {
        val theOrigin = LatLng(originAddress!!.latitude, originAddress!!.longitude)
        oriMarker = it.addMarker(MarkerOptions().position(theOrigin).title("Origin"))
      }

      if (destAddress != null) {
        val theDest = LatLng(destAddress!!.latitude, destAddress!!.longitude)
        destMarker = it.addMarker(MarkerOptions().position(theDest).title("Destination"))
      }

      // Zoom to live location
      if (latLng != null) {
        it.addMarker(MarkerOptions().position(latLng).title("Current"))
        it.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
      }
    }

    // Zoom to review two locations, if origin and destination address are present
    if (originAddress != null && destAddress != null) {
      val builder = LatLngBounds.Builder()
      builder.include(LatLng(originAddress!!.latitude, originAddress!!.longitude))
      builder.include(LatLng(destAddress!!.latitude, destAddress!!.longitude))

      val bounds = builder.build()
      val padding = 100

      map?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }
  }

  // Check actual location on map for inputted origin and destination address
  fun getActualAddresses(parsedOri: String, parsedDest: String) {
    GlobalScope.launch(Dispatchers.Main) {
      val geocoder = Geocoder(context)
      val originRes = parsedOri.let {
        geocoder.getFromLocationName(it, 1)
      }
      val destRes = parsedDest.let {
        geocoder.getFromLocationName(it, 1)
      }

      if (!originRes.isNullOrEmpty() && !destRes.isNullOrEmpty()) {
        val originArr: List<Address> = originRes
        val destArr: List<Address> = destRes

        userOrigin.value = originArr[0].getAddressLine(0)
        userDest.value = destArr[0].getAddressLine(0)

        originAddress = originArr[0]
        destAddress = destArr[0]

        addMarkers()
      } else {
        Toast.makeText(context, "Invalid address error", Toast.LENGTH_SHORT).show()
      }
    }
  }

  // Check user input location
  fun checkLocation() {
    if (userOrigin.value.isNotBlank() && userDest.value.isNotBlank()) {
      getActualAddresses(userOrigin.value, userDest.value)
    } else {
      Toast.makeText(context, "Please enter address for origin and destination.", Toast.LENGTH_LONG).show()
    }
  }

  // Get user live location
  fun getCurrentLocation() {
    fusedLocationClient.lastLocation
      .addOnSuccessListener { location ->
        if (location != null) {
          val latLng = LatLng(location.latitude, location.longitude)
          map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

          // Convert live location to address
          GlobalScope.launch(Dispatchers.Main) {
            val geocoder = Geocoder(context)
            val liveRes = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (!liveRes.isNullOrEmpty()) {
              val liveArr: List<Address> = liveRes

              liveAddress = liveArr[0].getAddressLine(0)
              origin.value = liveAddress
              userOrigin.value = liveAddress
              originAddress = liveArr[0]

              addMarkers(latLng)
            } else {
              Toast.makeText(context, "Failed to fetch live location", Toast.LENGTH_SHORT).show()
            }
          }
        } else {
          Toast.makeText(context, "Unable to fetch current location", Toast.LENGTH_SHORT).show()
        }
      }
      .addOnFailureListener { e ->
        Toast.makeText(context, "Error getting current location: ${e.message}", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
      }
  }

  // Request for permission
  val requestPermissionLaunch =
    rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
      if (isGranted) {
        getCurrentLocation()
      } else {
        Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
      }
    }

  BackHandler {
    onBack()
    navCtrl.navigate(UserRoutes.Shipment.routes) {
      launchSingleTop = true
    }
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(30.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Image(
        painter = painterResource(id = R.drawable.logo_nobg),
        contentDescription = "HaulEase_Logo",
        modifier = Modifier.size(100.dp)
      )

      Text(
        text = "Create Shipment",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 48.sp,
          textAlign = TextAlign.End
        )
      )
    }

    Spacer(modifier = Modifier.height(25.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .weight(1f)
    ) {
      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = name,
        onValueChange = { newValue ->
          name.value = newValue
          tempShipmentCargo.receiverName = newValue
        },
        label = "Receiver Name",
        isSingle = true
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = contact,
        onValueChange = { newValue ->
          contact.value = newValue
          tempShipmentCargo.receiverContact = newValue
        },
        label = "Receiver Contact",
        isSingle = true,
        onlyNumber = true
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = userOrigin,
        onValueChange = { newValue ->
          origin.value = newValue
          userOrigin.value = newValue
        },
        label = "Origin Address",
        maxLines = 5
      )

      Spacer(modifier = Modifier.height(8.dp))

      SimpleTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        inputText = userDest,
        onValueChange = { newValue ->
          dest.value = newValue
          userDest.value = newValue
        },
        label = "Destination Address",
        maxLines = 5
      )

      Spacer(modifier = Modifier.height(8.dp))

      Button(
        onClick = {
          checkLocation()
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(end = 120.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
        shape = RoundedCornerShape(5.dp),
        enabled = originOrDest
      ) {
        Text(
          text = "Check Location",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 20.sp,
            color = Color(0xFFE5E5E5)
          )
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      AndroidView(
        modifier = Modifier
          .fillMaxWidth()
          .height(400.dp)
          .clip(shape = RoundedCornerShape(5.dp)),
        factory = { ctx ->
          MapView(ctx).apply {
            onCreate(null)
            getMapAsync { googleMap ->
              map = googleMap
              requestPermissionLaunch.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
          }
        },
        update = { mapView ->
          mapView.getMapAsync { googleMap ->
            map = googleMap
          }
        }
      )

      Spacer(modifier = Modifier.height(25.dp))

      Text(
        text = "List of Cargos",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 28.sp,
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      tempShipmentCargo.cargoList.forEachIndexed { index, tempCargo ->
        SimpleCargoBox(
          cargo = tempCargo,
          cargoCount = index + 1,
          onRemove = {
            createCargoShipmentVM.removeCargo(tempCargo)
            navCtrl.navigate(UserInnerRoutes.CreateShipment.routes) {
              launchSingleTop = true
            }
          }
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      Button(
        onClick = {
          navCtrl.navigate(UserInnerRoutes.CreateCargo.routes)
        },
        modifier = Modifier
          .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
        shape = RoundedCornerShape(5.dp),
      ) {
        Text(
          text = "Add New Cargo",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 20.sp,
            color = Color(0xFFE5E5E5)
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = {
          cScope.launch {
            try {
              if (originAddress != null) {
                createCargoShipmentVM.placeShipment(
                  Shipment(
                    id = 0,
                    status = CargoStatus.status1.titleText,
                    origin = userOrigin.value,
                    destination = userDest.value,
                    receiverName = name.value,
                    receiverContact = contact.value,
                    consignorId = Sessions.sessionToken?.toInt(),
                    paymentId = 0,
                    trackingId = 0,
                    truckId = 0
                  ),
                  tempShipmentCargo,
                  originAddress!!,
                  context
                )
              }
            } catch (e: Exception) {
              Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
          }

          onBack()
          navCtrl.navigate(UserRoutes.Shipment.routes) {
            launchSingleTop = true
          }
        },
        modifier = Modifier
          .weight(0.35f),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
        shape = RoundedCornerShape(5.dp),
        enabled = allFieldsNotEmpty
      ) {
        Text(
          text = "Place",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 24.sp,
            color = Color(0xFFE5E5E5)
          )
        )
      }

      Spacer(modifier = Modifier.width(20.dp))

      Button(
        onClick = {
          onBack()
          navCtrl.navigate(UserRoutes.Shipment.routes) {
            launchSingleTop = true
          }
        },
        modifier = Modifier
          .weight(0.35f),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA111)),
        shape = RoundedCornerShape(5.dp),
      ) {
        Text(
          text = "Cancel",
          style = TextStyle(
            fontFamily = FontFamily(Font(R.font.squada)),
            fontSize = 24.sp,
          )
        )
      }
    }

    Spacer(modifier = Modifier.padding(bottom = 52.dp))
  }
}