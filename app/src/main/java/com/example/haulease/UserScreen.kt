package com.example.haulease

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haulease.navigations.UserRoutes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserScreen() {
  val navCtrl = rememberNavController()

  Scaffold(
    content = {
      UserNavHost(navCtrl = navCtrl)
    },
    bottomBar = {

    }
  )
}

@Composable
fun UserNavHost(navCtrl: NavHostController) {
  NavHost(
    navController = navCtrl,
    startDestination = "start",
    enterTransition = {
      slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(500)
      )
    },
    exitTransition = {
      slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(500)
      )
    },
    popEnterTransition = {
      slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(500)
      )
    },
    popExitTransition = {
      slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(500)
      )
    }
  ) {
    composable("Start") {
      navCtrl.navigate(UserRoutes.Dashboard.routes)
    }

    composable(UserRoutes.Dashboard.routes) {

    }

    composable(UserRoutes.Shipment.routes) {

    }

    composable(UserRoutes.History.routes) {

    }

    composable(UserRoutes.Profile.routes) {

    }
  }
}