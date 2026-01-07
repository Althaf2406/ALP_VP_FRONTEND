package com.example.alp_vp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.alp_vp_frontend.data.container.AppContainer
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.repository.AuthRepository
import com.example.alp_vp_frontend.ui.theme.ALP_VP_FRONTENDTheme
import com.example.alp_vp_frontend.ui.view.AuthScreen
import com.example.alp_vp_frontend.ui.view.FocusView
import com.example.alp_vp_frontend.ui.view.HomeView
import com.example.alp_vp_frontend.ui.view.LoginView
import com.example.alp_vp_frontend.ui.view.LogoutView
import com.example.alp_vp_frontend.ui.view.MoneyView
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel
import com.example.alp_vp_frontend.ui.viewmodel.FocusViewModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel
import com.example.alp_vp_frontend.ui.viewmodel.FocusPhaseViewModel

// =========================
// SCREEN STATE
// =========================
enum class Screen {
    FOCUS_LIST,
    FOCUS_DETAIL,
    HOME,
    MONEY,
    LOGOUT
}

enum class MoneyViewMode {
    LIST,
    GRAPH
}


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
                var viewMode by remember { mutableStateOf(MoneyViewMode.LIST) }


                // =========================
                // GLOBAL STATE
                // =========================
                var isLoggedIn by remember {
                    mutableStateOf(tokenManager.getToken() != null)
                }

                var currentScreen by remember {
                    mutableStateOf(Screen.MONEY)
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

//                val focusPhaseViewModel = remember {
//                    FocusPhaseViewModel(
//                        repository = appContainer.focusPhaseService
//                    )
//                }

                // =========================
                // RESTORE USER FROM TOKEN
                // =========================
                LaunchedEffect(Unit) {
                    authViewModel.restoreUserFromToken()
                }

                // =========================
                // UI ROUTING
                // =========================
                if (!isLoggedIn) {

                    AuthScreen(
                        authViewModel = authViewModel,
                        onLoginSuccess = {
                            isLoggedIn = false
                            currentScreen = Screen.LOGOUT
                        },
                        moneyViewModel = moneyViewModel
                    )

                } else {

                    when (currentScreen) {

                        Screen.FOCUS_LIST -> {
                            FocusView(
                                focusViewModel = focusViewModel,
                                onFocusClick = { focus ->
                                    selectedFocusId = focus.id
                                    currentScreen = Screen.FOCUS_DETAIL
                                }
                            )
                        }

//                        Screen.FOCUS_DETAIL -> {
//                            selectedFocusId?.let { focusId ->
//                                FocusPhaseScreen(
//                                    focusId = focusId,
//                                    viewModel = focusPhaseViewModel,
//                                    onBack = {
//                                        currentScreen = Screen.FOCUS_LIST
//                                    }
//                                )
//                            }
//                        }
                        Screen.FOCUS_DETAIL -> {
                            Text("Unknown Screen")
                        }

                        Screen.HOME -> {
                            HomeView(
                            )
                        }
                        Screen.MONEY -> {
                            MoneyView(
                                moneyViewModel = moneyViewModel,
                                authViewModel = authViewModel,
                                viewMode = viewMode,
                                onChangeViewMode = { viewMode = it }
                            )
                        }
                        Screen.LOGOUT -> {
                            LogoutView(
                                authViewModel = authViewModel,
                                moneyViewModel = moneyViewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}
