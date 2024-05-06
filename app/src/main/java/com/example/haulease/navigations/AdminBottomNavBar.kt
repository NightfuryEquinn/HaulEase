package com.example.haulease.navigations

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.compose.md_theme_light_background
import com.example.compose.md_theme_light_surfaceTint
import com.example.haulease.R

@Composable
fun AdminBottomNavBar(navCtrl: NavHostController) {
  NavigationBar(
    modifier = Modifier
      .height(64.dp)
  ) {
    val backStackEntry by navCtrl.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    BottomBar.BottomBarItems.forEach { navBarItem ->
      NavigationBarItem(
        selected = currentRoute == navBarItem.adminRoute,
        onClick = {
          navCtrl.navigate(navBarItem.adminRoute) {
            popUpTo(navCtrl.graph.findStartDestination().id) {
              saveState = true
            }

            launchSingleTop = true
            restoreState = true
          }
        },

        icon = {
          Icon(
            imageVector = navBarItem.image,
            contentDescription = navBarItem.title,
            tint = if (currentRoute == navBarItem.adminRoute) md_theme_light_background else Color.Unspecified,
            modifier = Modifier
              .align(Alignment.CenterVertically)
          )
        },

        label = {
          Text(
            text = navBarItem.title,
            style = TextStyle(
              fontFamily = FontFamily(Font(R.font.squada))
            ),
            modifier = Modifier
              .align(Alignment.CenterVertically)
          )
        },
        modifier = Modifier
          .fillMaxHeight()
          .padding(top = 12.dp, bottom = 8.dp),
        colors = NavigationBarItemColors(
          selectedIconColor = Color.Unspecified,
          selectedTextColor = Color.Unspecified,
          selectedIndicatorColor = md_theme_light_surfaceTint,
          unselectedIconColor = Color.Unspecified,
          unselectedTextColor = Color.Unspecified,
          disabledIconColor = Color.Unspecified,
          disabledTextColor = Color.Unspecified,
        )
      )
    }
  }
}