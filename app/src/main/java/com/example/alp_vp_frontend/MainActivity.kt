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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.alp_vp_frontend.data.container.AuthContainer
import com.example.alp_vp_frontend.data.container.MoneyContainer
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.repository.AuthRepository
import com.example.alp_vp_frontend.ui.theme.ALP_VP_FRONTENDTheme
import com.example.alp_vp_frontend.ui.view.AuthScreen
import com.example.alp_vp_frontend.ui.view.HomeView
import com.example.alp_vp_frontend.ui.view.LoginView
import com.example.alp_vp_frontend.ui.view.MoneyView
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ALP_VP_FRONTENDTheme {

                val context = this
                val tokenManager = remember { TokenManager(context) }
                val authContainer = remember { AuthContainer(context) }
                var isLoggedIn by remember {
                    mutableStateOf(tokenManager.getToken() != null)
                }

                val authViewModel = remember {
                    AuthViewModel(
                        repository = authContainer.authRepository,
                        tokenManager = tokenManager
                    )
                }

                if (!isLoggedIn) {
                    AuthScreen(
                        authViewModel = authViewModel,
                        onLoginSuccess = {
                            isLoggedIn = true
                        }
                    )
                } else {
                    HomeView(
                        onNavigateToMoney = { }
                    )
                }
            }
        }
    }
}
