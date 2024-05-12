package com.example.haulease.validations

import com.maxkeppeler.sheets.list.models.ListOption

object CargoStatus {
  val status1 = ListOption(titleText = "Driver on the way for cargo pickup")
  val status2 = ListOption(titleText = "Driver on the way to harbour")
  val status3 = ListOption(titleText = "Cargo arrived at harbour")
  val status4 = ListOption(titleText = "Cargo under inspection and measuring")
  val status5 = ListOption(titleText = "Cargo loaded onto deck")
  val status6 = ListOption(titleText = "Cargo has departed from harbour")
  val status7 = ListOption(titleText = "Cargo has arrived at harbour near destination")
  val status8 = ListOption(titleText = "Cargo is on the way to destination")
  val status9 = ListOption(titleText = "Cargo has arrived")
}
