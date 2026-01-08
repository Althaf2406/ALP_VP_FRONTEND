package com.example.alp_vp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.alp_vp_frontend.data.container.AppContainer
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.ui.route.AppRoute
import com.example.alp_vp_frontend.ui.route.Route
import com.example.alp_vp_frontend.ui.theme.ALP_VP_FRONTENDTheme
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel
import com.example.alp_vp_frontend.ui.viewmodel.FocusViewModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel
import com.example.alp_vp_frontend.ui.viewmodel.ActivityViewModel

// =========================
// SCREEN STATE
// =========================
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ALP_VP_FRONTENDTheme {
                // =========================
                // CORE DEPENDENCIES
                // =========================
                val context = this
                val tokenManager = remember { TokenManager(context) }
                val appContainer = remember { AppContainer(context) }

                // =========================
                // GLOBAL STATE
                // =========================
                var isLoggedIn by remember {
                    mutableStateOf(tokenManager.getToken() != null)
                }

                var currentScreen by remember {
                    mutableStateOf(Route.Home)
                }

                var selectedFocusId by remember {
                    mutableStateOf<Int?>(null)
                }

                // =========================
                // VIEWMODELS
                // =========================
                val authViewModel = remember {
                    AuthViewModel(
                        authRepository = appContainer.authRepository,
                        tokenManager = tokenManager
                    )
                }

                val moneyViewModel = remember {
                    MoneyViewModel(
                        repository = appContainer.moneyRepository
                    )
                }

                        
                val focusViewModel = remember {
                    FocusViewModel(
                        repository = appContainer.focusRepository
                    )
                }

                val activityViewModel = remember {
                    ActivityViewModel(
                        repository = appContainer.activityRepository
                    )
                }
                // =========================
                // RESTORE USER FROM TOKEN
                // =========================
                LaunchedEffect(Unit) {
                    authViewModel.restoreUserFromToken()
                }

                val startDestination =
                    if (tokenManager.getToken() != null) Route.Home.screenName
                    else Route.Home.screenName

                AppRoute(
                    startDestination = startDestination,
                    authViewModel = authViewModel,
                    moneyViewModel = moneyViewModel,
                    focusViewModel = focusViewModel,
                    activityViewModel = activityViewModel
                )


                // =========================
                // UI ROUTING
                // =========================
//                if (!isLoggedIn) {
//
//                    AuthScreen(
//                        authViewModel = authViewModel,
//                        onLoginSuccess = {
//                            isLoggedIn = false
//                            currentScreen = Route.Home
//                        },
//                        moneyViewModel = moneyViewModel
//                    )
//
//                } else {
//
//                    when (currentScreen) {
//
//
//                        Route.Home -> {
//                            HomeView()
//                        }
//                        Route.Finance -> {
//                            MoneyView(
//                                moneyViewModel = moneyViewModel,
//                                authViewModel = authViewModel
//                            )
//                        }
//                        Route.Logout -> {
//                            LogoutView(
//                                authViewModel = authViewModel,
//                                moneyViewModel = moneyViewModel,
//                            )
//                        }
//                        Route.Login -> {
//                            LoginView(
//                                authViewModel = authViewModel,
//                                moneyViewModel = moneyViewModel,
//                                onLoginSuccess = {},
//                                onNavigateRegister = {}
//                            )
//                        }
//                        Route.Finance -> {
//                            MoneyView(
//                                moneyViewModel = moneyViewModel,
//                                authViewModel = authViewModel
//                            )
//                        }
//                        Route.ActivityCalendar -> {
//                            ActivityView(
//                                ActivityRepository
//                            )
//                    }
                }
            }
        }
    }

