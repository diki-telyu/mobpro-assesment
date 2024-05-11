package org.d3if0166.dailytask.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("MainScreen")
    data object About: Screen("aboutScreen")
    data object Form: Screen("detailScreen")
}