package com.example.alp_vp_frontend.ui.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alp_vp_frontend.ui.view.ActivityView
import com.example.alp_vp_frontend.ui.view.DailyScheduleView
import com.example.alp_vp_frontend.ui.view.HomeScreen
import com.example.alp_vp_frontend.ui.view.LoginView
import com.example.alp_vp_frontend.ui.view.LogoutView
import com.example.alp_vp_frontend.ui.view.MoneyView
import com.example.alp_vp_frontend.ui.view.TimerView
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel
import com.example.alp_vp_frontend.ui.viewmodel.FocusViewModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel
import com.example.alp_vp_frontend.ui.viewmodel.ActivityViewModel


enum class Route(val screenName: String) {
    Home("home_screen"),
    Login("login_screen"),
    ActivityCalendar("activity_calendar_screen"),
    DailySchedule("daily_schedule_screen/{date}"),
    Finance("finance_screen"),
    Profile("profile_screen"),
    Logout("logout_screen"),
    Timer("timer_screen")
}

object RouteArgs {
    const val Date = "date"
    fun dailySchedule(date: String) = "daily_schedule_screen/$date"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoute(
    startDestination: String,
    authViewModel: AuthViewModel,
    moneyViewModel: MoneyViewModel,
    focusViewModel: FocusViewModel,
    activityViewModel: ActivityViewModel
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarScreens = listOf(
        Route.Home.screenName,
        Route.Finance.screenName,
        Route.ActivityCalendar.screenName,
        Route.Timer.screenName
    )

    val showBottomBar = currentDestination?.route in bottomBarScreens
    val isTopLevelDestination = currentDestination?.route in bottomBarScreens

    Scaffold(
        topBar = {
            if (!isTopLevelDestination && currentDestination?.route != Route.Login.screenName) {
                TopAppBar(
                    title = {
                        val title = when (currentDestination?.route) {
                            Route.DailySchedule.screenName -> "Daily Schedule"
                            else -> ""
                        }
                        Text(text = title)
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
//                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = Route.Home.screenName) {
                HomeScreen(navController)
            }
            composable(route = Route.Login.screenName) {
                LoginView(
                    authViewModel = authViewModel,
                    moneyViewModel = moneyViewModel,
                    onLoginSuccess = {
                        navController.navigate(Route.Home.screenName) {
                            popUpTo(Route.Login.screenName) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateRegister = { /* add register route if needed */ }
                )
            }
            composable(route = Route.Finance.screenName) {
                MoneyView(
                    moneyViewModel = moneyViewModel,
                    authViewModel = authViewModel
                )
            }
            composable(route = Route.Logout.screenName) {
                LogoutView(
                    authViewModel = authViewModel,
                    moneyViewModel = moneyViewModel
                ).also { 
                    navController.navigate(Route.Login.screenName) {
                        popUpTo(Route.Home.screenName) { inclusive = true }
                    }
                }
            }
            composable(route = Route.ActivityCalendar.screenName) {
                ActivityView(
                    navController = navController,
                    authViewModel = authViewModel,
                    activityViewModel = activityViewModel
                )
            }
            composable(
                route = Route.DailySchedule.screenName,
                arguments = listOf(navArgument(RouteArgs.Date) { type = NavType.StringType })
            ) { backStackEntry ->
                val dateStr = backStackEntry.arguments?.getString(RouteArgs.Date).orEmpty()
                DailyScheduleView(
                    calenderViewModel = activityViewModel,
                    date = dateStr
                )
            }
            composable(route = Route.Timer.screenName) {
                TimerView(focusViewModel = focusViewModel)
            }
        }
    }
}
