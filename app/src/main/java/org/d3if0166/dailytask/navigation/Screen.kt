package org.d3if0166.dailytask.navigation

import org.d3if0166.dailytask.ui.screen.KEY_ID_TASK

sealed class Screen(val route: String) {
    data object Home: Screen("MainScreen")
    data object Login: Screen("authentication")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_TASK}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
    data object History: Screen("historyScreen")
}