package com.example.alp_vp_frontend.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.mutablePreferencesOf
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel

@Composable
fun AuthScreen(
    moneyViewModel: MoneyViewModel,
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    var isLogin by remember { mutableStateOf(true) }

    if (isLogin) {
        LoginView(
            authViewModel = authViewModel,
            onLoginSuccess = {
                onLoginSuccess()
            },
            onNavigateRegister = {
                isLogin = false
            },
            moneyViewModel = moneyViewModel
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

