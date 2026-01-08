package com.example.alp_vp_frontend.navigation

sealed class Screen(val route: String) {
    object Summary : Screen("summary")
    object AddTask : Screen("addTask")
    object AddActivity : Screen("addActivity")

}