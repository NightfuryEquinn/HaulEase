package com.example.haulease.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun SimpleCargoBox(
  navCtrl: NavHostController,
  image: Painter,
  id: String? = null
) {
  val currentRoute = navCtrl.currentBackStackEntryAsState().value?.destination?.route

}