package com.example.haulease

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.haulease.navigations.BottomNavBar
import com.example.haulease.navigations.routes.UserInnerRoutes
import com.example.haulease.navigations.routes.UserRoutes
import com.example.haulease.viewmodels.user.inner.CreateCargoShipmentVM
import com.example.haulease.views.user.DashboardScreen
import com.example.haulease.views.user.HistoryScreen
import com.example.haulease.views.user.ProfileScreen
import com.example.haulease.views.user.ShipmentScreen
import com.example.haulease.views.user.inner.CargoDetailScreen
import com.example.haulease.views.user.inner.CreateCargoScreen
import com.example.haulease.views.user.inner.CreateShipmentScreen
import com.example.haulease.views.user.inner.PaymentScreen
import com.example.haulease.views.user.inner.ShipmentDetailScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserScreen() {
  val navCtrl = rememberNavController()
  val backStackEntry by navCtrl.currentBackStackEntryAsState()
  val currentRoute = backStackEntry?.destination?.route

  Scaffold(
    content = {
      UserNavHost(navCtrl = navCtrl)
    },
    bottomBar = {
      if (currentRoute != "MainScreen") {
        BottomNavBar(navCtrl = navCtrl)
      }
    }
  )
}

@Composable
fun UserNavHost(navCtrl: NavHostController) {
  // Shared view model between create cargo and shipment screen
  val createCargoShipmentVM: CreateCargoShipmentVM = viewModel()

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
      navCtrl.navigate(UserRoutes.Dashboard.routes)
    }

    // User Routes
    composable(UserRoutes.Dashboard.routes) {
      DashboardScreen(
        navCtrl = navCtrl
      )
    }

    composable(UserRoutes.Shipment.routes) {
      ShipmentScreen(
        navCtrl = navCtrl
      )
    }

    composable(UserRoutes.History.routes) {
      HistoryScreen(
        navCtrl = navCtrl
      )
    }

    composable(UserRoutes.Profile.routes) {
      ProfileScreen(
        navCtrl = navCtrl
      )
    }

    // User Inner Routes
    composable(
      route = "ShipmentDetail?shipmentId={shipmentId}",
      arguments = listOf(
        navArgument("shipmentId") {
          type = NavType.StringType
          nullable = true
        }
      )
    ) { backStackEntry ->
      val shipmentId = backStackEntry.arguments?.getString("shipmentId")

      ShipmentDetailScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        },
        shipmentId = shipmentId!!.toInt()
      )
    }

    composable(
      route = "CargoDetail?cargoId={cargoId}&shipmentId={shipmentId}",
      arguments = listOf(
        navArgument("cargoId") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("shipmentId") {
          type = NavType.StringType
          nullable = true
        }
      )
    ) { backStackEntry ->
      val cargoId = backStackEntry.arguments?.getString("cargoId")
      val shipmentId = backStackEntry.arguments?.getString("shipmentId")

      CargoDetailScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        },
        cargoId = cargoId!!.toInt(),
        shipmentId = shipmentId!!.toInt()
      )
    }

    composable(UserInnerRoutes.CreateShipment.routes) {
      CreateShipmentScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        },
        createCargoShipmentVM = createCargoShipmentVM
      )
    }

    composable(UserInnerRoutes.CreateCargo.routes) {
      CreateCargoScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        },
        createCargoShipmentVM = createCargoShipmentVM
      )
    }

//    composable(
//      route = "CreateShipment?shipmentId={shipmentId}",
//      arguments = listOf(
//        navArgument("shipmentId") {
//          type = NavType.StringType
//          nullable = true
//        },
//      )
//    ) { backStackEntry ->
//      val shipmentId = backStackEntry.arguments?.getString("shipmentId")
//
//      CreateShipmentScreen(
//        navCtrl = navCtrl,
//        onBack = {
//          navCtrl.popBackStack()
//        },
//        shipmentId = shipmentId?.toIntOrNull(),
//        createCargoShipmentVM = createCargoShipmentVM,
//      )
//    }
//
//    composable(
//      route = "CreateCargo?shipmentId={shipmentId}",
//      arguments = listOf(
//        navArgument("shipmentId") {
//          type = NavType.StringType
//          nullable = true
//        }
//      )
//    ) { backStackEntry ->
//      val shipmentId = backStackEntry.arguments?.getString("shipmentId")
//
//      CreateCargoScreen(
//        navCtrl = navCtrl,
//        onBack = {
//          navCtrl.popBackStack()
//        },
//        shipmentId = shipmentId?.toIntOrNull(),
//        createCargoShipmentVM = createCargoShipmentVM
//      )
//    }

    composable(
      route = "Payment?paymentId={paymentId}&shipmentId={shipmentId}",
      arguments = listOf(
        navArgument("paymentId") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("shipmentId") {
          type = NavType.StringType
          nullable = true
        }
      )
    ) { backStackEntry ->
      val paymentId = backStackEntry.arguments?.getString("paymentId")
      val shipmentId = backStackEntry.arguments?.getString("shipmentId")

      PaymentScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        },
        paymentId = paymentId!!.toInt(),
        shipmentId = shipmentId!!.toInt()
      )
    }

    composable("MainScreen") {
      MainScreen()
    }
  }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
  navCtrl: NavHostController
): T {
  val navGraphRoute = destination.parent?.route ?: return viewModel()
  val parentEntry = remember(this) {
    navCtrl.getBackStackEntry(navGraphRoute)
  }
  return viewModel(parentEntry)
}