package com.example.haulease.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haulease.R

@Composable
fun SimplePaymentBox(
  originTruckTravelFees: Double? = null,
  originTruckLoadingFees: Double? = null,
  harborMeasurementFees: Double? = null,
  harborLoadingFees: Double? = null,
  totalCargoFees: Double? = null,
  harborUnloadingFees: Double? = null,
  destTruckLoadingFees: Double? = null,
  destTruckTravelFees: Double? = null,
  onPayClick: () -> Unit
) {
  var totalPayable: Double = 0.0

  totalPayable = if (originTruckTravelFees != null && originTruckLoadingFees != null && harborMeasurementFees != null && harborLoadingFees != null) {
    originTruckTravelFees + originTruckLoadingFees + harborMeasurementFees + harborLoadingFees
  } else if (totalCargoFees != null) {
    val customFees = 0.1 * totalCargoFees
    totalCargoFees + customFees
  } else {
    harborUnloadingFees!! + destTruckLoadingFees!! + destTruckTravelFees!!
  }

  Column(
    modifier = Modifier
      .clip(shape = RoundedCornerShape(5.dp))
      .fillMaxWidth()
      .background(Color(0xFFE5E5E5))
      .padding(12.dp)
  ) {
    if (originTruckTravelFees != null) {
      Text(
        buildAnnotatedString {
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          ) {
            append("Truck Travel Fees: ")
          }
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.librebold)),
              fontSize = 12.sp,
            )
          ) {
            append("RM $originTruckTravelFees")
          }
        }
      )
    }

    if (originTruckLoadingFees != null) {
      Text(
        buildAnnotatedString {
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          ) {
            append("Truck Loading Fees: ")
          }
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.librebold)),
              fontSize = 12.sp,
            )
          ) {
            append("RM $originTruckLoadingFees")
          }
        }
      )
    }

    if (harborMeasurementFees != null) {
      Text(
        buildAnnotatedString {
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          ) {
            append("Harbor Measurement Fees: ")
          }
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.librebold)),
              fontSize = 12.sp,
            )
          ) {
            append("RM $harborMeasurementFees")
          }
        }
      )
    }

    if (harborLoadingFees != null) {
      Text(
        buildAnnotatedString {
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          ) {
            append("Harbor Loading Fees: ")
          }
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.librebold)),
              fontSize = 12.sp,
            )
          ) {
            append("RM $harborLoadingFees")
          }
        }
      )
    }

    if (totalCargoFees != null) {
      val customFees: Double = 0.1 * totalCargoFees.toDouble()

      Text(
        buildAnnotatedString {
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          ) {
            append("Total Cargo Fees: ")
          }
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.librebold)),
              fontSize = 12.sp,
            )
          ) {
            append("RM $totalCargoFees")
          }
        }
      )

      Text(
        text = "Maximum value between actual weight or volumetric weight",
        style = TextStyle(
          fontStyle = FontStyle.Italic,
          fontSize = 10.sp
        )
      )
      
      Spacer(modifier = Modifier.height(5.dp))

      Text(
        buildAnnotatedString {
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          ) {
            append("Custom Fees (10% of cargo): ")
          }
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.librebold)),
              fontSize = 12.sp,
            )
          ) {
            append("RM $customFees")
          }
        }
      )
    }

    if (destTruckTravelFees != null) {
      Text(
        buildAnnotatedString {
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          ) {
            append("Truck Travel Fees: ")
          }
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.librebold)),
              fontSize = 12.sp,
            )
          ) {
            append("RM $destTruckTravelFees")
          }
        }
      )
    }

    if (destTruckLoadingFees != null) {
      Text(
        buildAnnotatedString {
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          ) {
            append("Truck Loading Fees: ")
          }
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.librebold)),
              fontSize = 12.sp,
            )
          ) {
            append("RM $destTruckLoadingFees")
          }
        }
      )
    }

    if (harborUnloadingFees != null) {
      Text(
        buildAnnotatedString {
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.libre)),
              fontSize = 12.sp
            )
          ) {
            append("Harbor Unloading Fees: ")
          }
          withStyle(
            style = SpanStyle(
              fontFamily = FontFamily(Font(R.font.librebold)),
              fontSize = 12.sp,
            )
          ) {
            append("RM $harborUnloadingFees")
          }
        }
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
      text = "Total Payable: RM $totalPayable",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.squada)),
        fontSize = 24.sp,
      )
    )
  }

  Spacer(modifier = Modifier.height(5.dp))

  Button(
    onClick = {
      onPayClick()
    },
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 160.dp),
    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311)),
    shape = RoundedCornerShape(5.dp)
  ) {
    Text(
      text = "Pay",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.squada)),
        fontSize = 24.sp,
      )
    )
  }
}