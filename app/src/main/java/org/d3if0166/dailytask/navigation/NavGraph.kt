package org.d3if0166.dailytask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if0166.dailytask.ui.screen.AboutScreen
import org.d3if0166.dailytask.ui.screen.DetailScreen
import org.d3if0166.dailytask.ui.screen.HistoryScreen
import org.d3if0166.dailytask.ui.screen.KEY_ID_TASK
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
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_TASK) { type = NavType.LongType }
            )
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_TASK)
            DetailScreen(navController, id)
        }

        composable(route = Screen.History.route) {
            HistoryScreen(navController)
        }
    }
}