package com.example.haulease

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.haulease.navigations.BottomNavBar
import com.example.haulease.navigations.routes.UserInnerRoutes
import com.example.haulease.navigations.routes.UserRoutes
import com.example.haulease.validations.InputValidation.isValidUri
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

  Scaffold(
    content = {
      UserNavHost(navCtrl = navCtrl)
    },
    bottomBar = {
      BottomNavBar(navCtrl = navCtrl)
    }
  )
}

@Composable
fun UserNavHost(navCtrl: NavHostController) {
  val context = LocalContext.current

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
    composable(UserInnerRoutes.ShipmentDetail.routes) {
      ShipmentDetailScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        }
      )
    }

    composable(UserInnerRoutes.CargoDetail.routes) {
      CargoDetailScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        }
      )
    }

    composable(UserInnerRoutes.CreateShipment.routes) {
      CreateShipmentScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        }
      )
    }

    composable(
      route = "CreateCargo?type={type}&weight={weight}&length={length}&width={width}&height={height}&desc={desc}&image={image}",
      arguments = listOf(
        navArgument("type") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("weight") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("length") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("width") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("height") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("desc") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("image") {
          type = NavType.StringType
          nullable = true
        }
      )
    ) { backStackEntry ->
      val cargoType = backStackEntry.arguments?.getString("type")
      val cargoWeight = backStackEntry.arguments?.getString("weight")
      val cargoLength = backStackEntry.arguments?.getString("length")
      val cargoWidth = backStackEntry.arguments?.getString("width")
      val cargoHeight = backStackEntry.arguments?.getString("height")
      val cargoDesc = backStackEntry.arguments?.getString("desc")
      val cargoImage = backStackEntry.arguments?.getString("image")

      CreateCargoScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        },
        cargoType = cargoType!!,
        cargoWeight = cargoWeight!!,
        cargoLength = cargoLength!!,
        cargoWidth = cargoWidth!!,
        cargoHeight = cargoHeight!!,
        cargoDesc = cargoDesc!!,
        cargoImage = isValidUri(cargoImage!!, context)
      )
    }

    composable(UserInnerRoutes.Payment.routes) {
      PaymentScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        }
      )
    }

    composable("MainScreen") {
      MainScreen()
    }
  }
}