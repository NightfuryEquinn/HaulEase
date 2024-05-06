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
import com.example.haulease.navigations.AdminBottomNavBar
import com.example.haulease.navigations.routes.AdminInnerRoutes
import com.example.haulease.navigations.routes.AdminRoutes
import com.example.haulease.views.admin.AdminDashboardScreen
import com.example.haulease.views.admin.AdminHistoryScreen
import com.example.haulease.views.admin.AdminProfileScreen
import com.example.haulease.views.admin.AdminShipmentScreen
import com.example.haulease.views.admin.inner.AdminCargoDetailScreen
import com.example.haulease.views.admin.inner.AdminEditCargoScreen
import com.example.haulease.views.admin.inner.AdminShipmentDetailScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminScreen() {
  val navCtrl = rememberNavController()

  Scaffold (
    content = {
      AdminNavHost(navCtrl = navCtrl)
    },
    bottomBar = {
      AdminBottomNavBar(navCtrl = navCtrl)
    }
  )
}

@Composable
fun AdminNavHost(navCtrl: NavHostController) {
  NavHost(
    navController = navCtrl,
    startDestination = "start",
    enterTransition = {
      slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(500)
      )
    },
    exitTransition = {
      slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(500)
      )
    },
    popEnterTransition = {
      slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(500)
      )
    },
    popExitTransition = {
      slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(500)
      )
    }
  ) {
    composable("start") {
      navCtrl.navigate(AdminRoutes.AdminDashboard.routes)
    }

    // Admin Routes
    composable(AdminRoutes.AdminDashboard.routes) {
      AdminDashboardScreen(
        navCtrl = navCtrl
      )
    }

    composable(AdminRoutes.AdminShipment.routes) {
      AdminShipmentScreen(
        navCtrl = navCtrl
      )
    }

    composable(AdminRoutes.AdminHistory.routes) {
      AdminHistoryScreen(
        navCtrl = navCtrl
      )
    }

    composable(AdminRoutes.AdminProfile.routes) {
      AdminProfileScreen(
        navCtrl = navCtrl
      )
    }

    // Admin Inner Routes
    composable(AdminInnerRoutes.AdminShipmentDetail.routes) {
      AdminShipmentDetailScreen(
        navCtrl = navCtrl
      )
    }

    composable(AdminInnerRoutes.AdminCargoDetail.routes) {
      AdminCargoDetailScreen(
        navCtrl = navCtrl
      )
    }

    composable(AdminInnerRoutes.AdminEditCargo.routes) {
      AdminEditCargoScreen(
        navCtrl = navCtrl
      )
    }
  }
}