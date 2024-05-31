package com.example.haulease

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
  val backStackEntry by navCtrl.currentBackStackEntryAsState()
  val currentRoute = backStackEntry?.destination?.route

  Scaffold (
    content = {
      AdminNavHost(navCtrl = navCtrl)
    },
    bottomBar = {
      if (currentRoute != "MainScreen") {
        AdminBottomNavBar(navCtrl = navCtrl)
      }
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
    // Parsing argument to pass data between screens
    composable(
      route = AdminInnerRoutes.AdminShipmentDetail.routes,
      arguments = listOf(
        navArgument("shipmentId") {
          type = NavType.StringType
        },
        navArgument("consignorId") {
          type = NavType.StringType
        }
      )
    ) { backStackEntry ->
      val shipmentId = backStackEntry.arguments?.getString("shipmentId") ?: ""
      val consignorId = backStackEntry.arguments?.getString("consignorId") ?: ""

      AdminShipmentDetailScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        },
        shipmentId = shipmentId.toInt(),
        consignorId = consignorId.toInt()
      )
    }

    // Parsing argument to pass data between screens
    composable(
      route = AdminInnerRoutes.AdminCargoDetail.routes,
      arguments = listOf(
        navArgument("cargoId") {
          type = NavType.StringType
        },
        navArgument("shipmentId") {
          type = NavType.StringType
        },
        navArgument("consignorId") {
          type = NavType.StringType
        }
      )
    ) { backStackEntry ->
      val cargoId = backStackEntry.arguments?.getString("cargoId") ?: ""
      val shipmentId = backStackEntry.arguments?.getString("shipmentId") ?: ""
      val consignorId = backStackEntry.arguments?.getString("consignorId") ?: ""

      AdminCargoDetailScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        },
        cargoId = cargoId.toInt(),
        shipmentId = shipmentId.toInt(),
        consignorId = consignorId.toInt()
      )
    }

    // Parsing argument to pass data between screens
    composable(
      route = AdminInnerRoutes.AdminEditCargo.routes,
      arguments = listOf(
        navArgument("cargoId") {
          type = NavType.StringType
        },
        navArgument("shipmentId") {
          type = NavType.StringType
        },
        navArgument("consignorId") {
          type = NavType.StringType
        },
        navArgument("type") {
          type = NavType.StringType
        },
        navArgument("weight") {
          type = NavType.StringType
        },
        navArgument("length") {
          type = NavType.StringType
        },
        navArgument("width") {
          type = NavType.StringType
        },
        navArgument("height") {
          type = NavType.StringType
        },
        navArgument("image") {
          type = NavType.StringType
        },
        navArgument("desc") {
          type = NavType.StringType
        }
      )
    ) { backStackEntry ->
      val cargoId = backStackEntry.arguments?.getString("cargoId") ?: ""
      val shipmentId = backStackEntry.arguments?.getString("shipmentId") ?: ""
      val consignorId = backStackEntry.arguments?.getString("consignorId") ?: ""
      val cargoType = backStackEntry.arguments?.getString("type") ?: ""
      val cargoWeight = backStackEntry.arguments?.getString("weight") ?: ""
      val cargoLength = backStackEntry.arguments?.getString("length") ?: ""
      val cargoWidth = backStackEntry.arguments?.getString("width") ?: ""
      val cargoHeight = backStackEntry.arguments?.getString("height") ?: ""
      val cargoImage = backStackEntry.arguments?.getString("image") ?: ""
      val cargoDesc = backStackEntry.arguments?.getString("desc") ?: ""

      AdminEditCargoScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        },
        cargoId = cargoId.toInt(),
        shipmentId = shipmentId.toInt(),
        consignorId = consignorId.toInt(),
        cargoType = cargoType,
        cargoWeight = cargoWeight,
        cargoLength = cargoLength,
        cargoWidth = cargoWidth,
        cargoHeight = cargoHeight,
        cargoImage = cargoImage,
        cargoDesc = cargoDesc
      )
    }

    composable("MainScreen") {
      MainScreen()
    }
  }
}