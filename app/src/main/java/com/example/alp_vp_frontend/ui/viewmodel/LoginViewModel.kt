package com.example.alp_vp_frontend.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(userRepository: UserRepository) : ViewModel() {

    // State untuk status login: "Idle", "Loading", "Success", "Error"
    private val _loginState = MutableStateFlow<String>("Idle")
    val loginState: StateFlow<String> = _loginState.asStateFlow()

    // Fungsi Login
    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loginState.value = "Loading"

            // --- SIMULASI PENGECEKAN KE SERVER ---
            // Nanti di sini Anda panggil: repository.login(email, pass)
            delay(1500) // Pura-pura loading 1.5 detik

            if (email == "admin@admin.com" && pass == "123456") {
                _loginState.value = "Success"
            } else {
                _loginState.value = "Error"
            }
            // -------------------------------------
        }
    }

    // Fungsi untuk reset status (misal setelah logout atau ingin login ulang)
    fun resetLoginState() {
        _loginState.value = "Idle"
    }
}
