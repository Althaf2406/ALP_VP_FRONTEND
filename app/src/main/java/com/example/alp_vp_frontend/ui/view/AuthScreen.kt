package com.example.alp_vp_frontend.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    var isLogin by remember { mutableStateOf(true) }

    if (isLogin) {
        LoginView(
            viewModel = authViewModel,
            onLoginSuccess = {
                onLoginSuccess()
            },
            onNavigateRegister = {
                isLogin = false
            }
        )
    } else {
        RegisterView(
            viewModel = authViewModel,
            onRegisterSuccess = {
                isLogin = true
            },
            onBackToLogin = {
                isLogin = true
            }
        )
    }
}

