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
import com.example.haulease.models.Sessions
import com.example.haulease.navigations.routes.SharedRoutes
import com.example.haulease.views.ForgotScreen
import com.example.haulease.views.LoginScreen
import com.example.haulease.views.RegisterScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
  val navCtrl = rememberNavController()

  Scaffold (
    content = {
      MainNavHost(navCtrl = navCtrl)
    },
    bottomBar = {

    }
  )
}

@Composable
fun MainNavHost(navCtrl: NavHostController) {
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
      if (Sessions.sessionToken != null && Sessions.sessionRole == "User") {
        navCtrl.navigate("UserScreen")
      } else if (Sessions.sessionToken != null && Sessions.sessionRole == "Admin") {
        navCtrl.navigate("AdminScreen")
      } else {
        navCtrl.navigate(SharedRoutes.Login.routes)
      }
    }

    composable(SharedRoutes.Login.routes) {
      LoginScreen(
        navCtrl = navCtrl
      )
    }

    composable(SharedRoutes.Register.routes) {
      RegisterScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        }
      )
    }

    composable(SharedRoutes.Forgot.routes) {
      ForgotScreen(
        navCtrl = navCtrl,
        onBack = {
          navCtrl.popBackStack()
        }
      )
    }

    composable("UserScreen") {
      UserScreen()
    }

    composable("AdminScreen") {
      AdminScreen()
    }
  }
}