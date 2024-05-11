package org.d3if0166.dailytask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if0166.dailytask.ui.screen.AboutScreen
import org.d3if0166.dailytask.ui.screen.DetailScreen
import org.d3if0166.dailytask.ui.screen.MainScreen


@Composable
fun SetupNavGraph(navController: NavController = rememberNavController()) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Form.route) {
            DetailScreen()
        }
    }
}