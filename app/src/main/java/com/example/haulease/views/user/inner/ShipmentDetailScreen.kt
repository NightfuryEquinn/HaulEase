package com.example.haulease.views.user.inner

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.haulease.R
import com.example.haulease.models.Cargo
import com.example.haulease.models.ShipmentPayment
import com.example.haulease.models.ShipmentTracking
import com.example.haulease.models.ShipmentTruck
import com.example.haulease.navigations.routes.UserRoutes
import com.example.haulease.ui.components.SimpleEmptyBox
import com.example.haulease.ui.components.SimpleViewBox
import com.example.haulease.validations.CargoStatus
import com.example.haulease.viewmodels.user.inner.ShipmentDetailState
import com.example.haulease.viewmodels.user.inner.ShipmentDetailVM
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("MissingPermission")
@Composable
fun ShipmentDetailScreen(
  navCtrl: NavHostController,
  onBack: () -> Unit,
  shipmentId: Int = 0,
  shipmentDetailVM: ShipmentDetailVM = viewModel()
) {
  val cScope = rememberCoroutineScope()
  val theShipmentDetail: ShipmentPayment? = shipmentDetailVM.theShipmentDetail
  val theShipmentTracking: ShipmentTracking? = shipmentDetailVM.theShipmentTracking
  val theShipmentTruck: ShipmentTruck? = shipmentDetailVM.theShipmentTruck
  val theShipmentCargos: List<Cargo> = shipmentDetailVM.theShipmentCargos

  // Map variables
  val context = LocalContext.current
  val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

  var map: GoogleMap? by remember { mutableStateOf(null) }
  var simMovement = 0.0
  var shipmentMarker: Marker? by remember { mutableStateOf(null) }
  var updateTime by remember { mutableStateOf("") }

  val trackingCoordinates by remember { mutableStateOf(
    LatLng(0.0, 0.0)
  )}
  val originAddress by remember { mutableStateOf<Address?>(null)}
  val destAddress by remember { mutableStateOf<Address?>(null)}

  // Observer
  val shipmentDetailState by shipmentDetailVM.shipmentDetailState.collectAsState()

  // Add markers on map
  fun addMarkers(latLng: LatLng) {
    // Remove shipment marker
    shipmentMarker?.remove()

    map?.let {
      if (originAddress != null) {
        val theOrigin = LatLng(originAddress!!.latitude, originAddress!!.longitude)
        it.addMarker(MarkerOptions().position(theOrigin).title("Origin"))
      }

      if (destAddress != null) {
        val theDest = LatLng(destAddress!!.latitude, destAddress!!.longitude)
        it.addMarker(MarkerOptions().position(theDest).title("Destination"))
      }

      shipmentMarker = it.addMarker(MarkerOptions().position(latLng).title("Shipment"))
    }
  }

  // Update shipment as simulation within Selangor
  fun updateShipmentLocation() {
    val latitude = trackingCoordinates.latitude + simMovement * (3.2975 - 2.9975)
    val longitude = trackingCoordinates.longitude + simMovement * (101.7349 - 101.3749)

    val newLocation = LatLng(latitude, longitude)
    addMarkers(newLocation)

    map?.animateCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 12.5f))

    simMovement += 0.025

    updateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
  }

  // Convert address to coordinates and place markers
  fun convertOriDestToCoordinates(oriAddress: Address, destAddress: Address) {
    val oriLatLng = LatLng(oriAddress.latitude, oriAddress.longitude)
    val destLatLng = LatLng(destAddress.latitude, destAddress.longitude)

    addMarkers(oriLatLng)
    addMarkers(destLatLng)
  }

  // Get user live location
  fun getCurrentLocation() {
    fusedLocationClient.lastLocation
      .addOnSuccessListener { location ->
        if (location != null) {
          val latLng = LatLng(location.latitude, location.longitude)
          map?.addMarker(MarkerOptions().position(latLng).title("Current"))
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
        text = "Shipment Details",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.squada)),
          fontSize = 48.sp,
          textAlign = TextAlign.End
        )
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    when (shipmentDetailState) {
      is ShipmentDetailState.SUCCESS -> {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .weight(1f)
        ) {
          Column(
            modifier = Modifier
              .clip(shape = RoundedCornerShape(5.dp))
              .fillMaxWidth()
              .background(Color(0xFFE5E5E5))
              .padding(12.dp),
          ) {
            Text(
              text = "Truck & Driver Details",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.squada)),
                fontSize = 20.sp,
                color = Color(0xFFFCA111)
              )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "Truck ID: ${theShipmentTruck?.truck?.id}",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.libre)),
                fontSize = 12.sp
              )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
              text = "Driver Name: ${theShipmentTruck?.truck?.driverName}",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.libre)),
                fontSize = 12.sp
              )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
              text = "License Plate: ${theShipmentTruck?.truck?.licensePlate}",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.libre)),
                fontSize = 12.sp
              )
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
              text = "Receiver Details",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.squada)),
                fontSize = 20.sp,
                color = Color(0xFFFCA111)
              )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "Receiver Name: ${theShipmentDetail?.shipment?.receiverName}",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.libre)),
                fontSize = 12.sp
              )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
              text = "Receiver Contact: ${theShipmentDetail?.shipment?.receiverContact}",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.libre)),
                fontSize = 12.sp
              )
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
              text = "Status",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.squada)),
                fontSize = 20.sp,
                color = Color(0xFFFCA111)
              )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = theShipmentDetail?.shipment?.status.toString(),
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.libre)),
                fontSize = 12.sp
              )
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
              text = "Origin to Destination",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.squada)),
                fontSize = 20.sp,
                color = Color(0xFFFCA111)
              )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = theShipmentDetail?.shipment?.origin.toString(),
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.libre)),
                fontSize = 12.sp,
              )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Icon(
              imageVector = Icons.Filled.ArrowDownward,
              contentDescription = null,
              tint = Color(0xFF14213D),
              modifier = Modifier
                .size(36.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
              text = theShipmentDetail?.shipment?.destination.toString(),
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.libre)),
                fontSize = 12.sp,
              )
            )
          }

          Spacer(modifier = Modifier.height(25.dp))

          Text(
            text = "Tracking Map",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada)),
              fontSize = 28.sp,
            )
          )

          Spacer(modifier = Modifier.height(5.dp))

          Text(
            text = "Last updated at $updateTime",
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp,
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

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
                  requestPermissionLaunch.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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

          theShipmentCargos.forEach {
            SimpleViewBox(
              navCtrl = navCtrl,
              modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .fillMaxSize()
                .background(Color(0xFFE5E5E5)),
              rowModifier = Modifier
                .fillMaxSize(),
              fromDatabase = true,
              imageFromDatabase = it.image,
              imageSize = 50,
              shipmentId = shipmentId,
              cargoId = it.id,
            )

            Spacer(modifier = Modifier.height(5.dp))
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          if (theShipmentDetail?.shipment?.status != CargoStatus.status9.titleText) {
            Button(
              onClick = {
                navCtrl.navigate("Payment?paymentId=${theShipmentDetail?.payment?.id}&shipmentId=$shipmentId")
              },
              modifier = Modifier
                .weight(0.35f),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
              shape = RoundedCornerShape(5.dp),
            ) {
              Text(
                text = "Payment",
                style = TextStyle(
                  fontFamily = FontFamily(Font(R.font.squada)),
                  fontSize = 24.sp,
                  color = Color(0xFFE5E5E5)
                )
              )
            }
          } else {
            Button(
              onClick = {
                // TODO()
                navCtrl.navigate(UserRoutes.Shipment.routes)
              },
              modifier = Modifier
                .weight(0.35f),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF14213D)),
              shape = RoundedCornerShape(5.dp),
            ) {
              Text(
                text = "Delivered?",
                style = TextStyle(
                  fontFamily = FontFamily(Font(R.font.squada)),
                  fontSize = 24.sp,
                  color = Color(0xFFE5E5E5)
                )
              )
            }
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
              text = "Back",
              style = TextStyle(
                fontFamily = FontFamily(Font(R.font.squada)),
                fontSize = 24.sp,
              )
            )
          }
        }
      }
      is ShipmentDetailState.LOADING -> {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
        )
      }
      is ShipmentDetailState.INITIAL -> {
        SimpleEmptyBox(
          modifier = Modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .height(150.dp)
            .fillMaxSize()
            .background(Color(0xFFE5E5E5))
            .weight(1f),
          colModifier = Modifier
            .fillMaxSize(),
          image = painterResource(id = R.drawable.close),
          name = "Unable to Load Information"
        )

        Spacer(modifier = Modifier.padding(bottom = 60.dp))
      }
    }

    Spacer(modifier = Modifier.padding(bottom = 52.dp))
  }

  LaunchedEffect(true) {
    requestPermissionLaunch.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

    getCurrentLocation()

    if (originAddress != null && destAddress != null) {
      convertOriDestToCoordinates(originAddress!!, destAddress!!)
    }

    flow {
      while (true) {
        emit(Unit)
        delay(1 * 15 * 1000)
      }
    }
      .flowOn(Dispatchers.IO)
      .onEach {
        updateShipmentLocation()
      }
      .launchIn(this)
  }

  DisposableEffect(Unit) {
    val job = cScope.launch {
      shipmentDetailVM.checkShipmentDetail(
        shipmentId,
        context
      )
    }

    onDispose {
      job.cancel()
      shipmentDetailVM.clearShipmentDetail()
    }
  }
}