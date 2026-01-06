package com.example.alp_vp_frontend.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.dto.RegisterRequest
import com.example.alp_vp_frontend.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    // ============================
    // UI STATE
    // ============================
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // ============================
    // LOGIN
    // ============================
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val success = repository.login(email, password)

            if (success) {
                onSuccess()
            } else {
                _error.value = "Email atau password salah"
            }

            _loading.value = false
        }
    }

    // ============================
    // REGISTER
    // ============================
    fun register(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val success = repository.register(
                RegisterRequest(
                    username = username,
                    email = email,
                    password = password
                )
            )

            if (success) {
                onSuccess()
            } else {
                _error.value = "Gagal melakukan registrasi"
            }

            _loading.value = false
        }
    }

    // ============================
    // LOGOUT
    // ============================
    fun logout() {
        repository.logout()
    }

    // ============================
    // CHECK LOGIN
    // ============================
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}
