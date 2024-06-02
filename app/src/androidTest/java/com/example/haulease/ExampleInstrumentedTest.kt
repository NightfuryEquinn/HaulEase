package com.example.haulease

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HaulEaseInstrumentedTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  // Context of the app under test.
  @Test
  fun useAppContext() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("com.example.haulease", appContext.packageName)
  }

  // Test on register page

  // Test on login page

  // Test on forgot page

  // Test on create shipment page

  // Test on create cargo page

  // Test on admin update shipment status

  // Test on admin update cargo details

}