package com.example.haulease.validations

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.haulease.R

object InputValidation {
  // Function to validate email address
  fun isValidEmail(email: String) : Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  // Function to validate password (at least 8 characters)
  fun isValidPassword(password: String): Boolean {
    return password.length >= 8
  }

  // Function to validate is integer, double, big decimal or not
  fun isValidInt(theInt: String): Boolean {
    val isInt = theInt.toIntOrNull()
    val isDouble = theInt.toDoubleOrNull()
    val isBigDecimal = theInt.toBigDecimalOrNull()

    return isInt != null || isDouble != null || isBigDecimal != null
  }

  // Function to validate and convert string to correct uri
  fun isValidUri(imageUriString: String, context: Context): Uri {
    val imageUri = Uri.parse(imageUriString)

    return when (imageUri.scheme) {
      "content" -> {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        context.contentResolver.query(imageUri, projection, null, null, null)?.use { cursor ->
          val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
          cursor.moveToFirst()
          val path = cursor.getString(columnIndex)
          Uri.parse(path ?: imageUri.toString())
        } ?: imageUri
      }
      "file" -> {
        imageUri
      }
      else -> {
        val resId = R.drawable.image
        val defaultImageUri = Uri.parse("android.resource://${context.packageName}/$resId")

        defaultImageUri
      }
    }
  }
}
