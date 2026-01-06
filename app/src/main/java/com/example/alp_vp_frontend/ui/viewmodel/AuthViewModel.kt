package com.example.alp_vp_frontend.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.repository.AuthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (token != null) {
                        tokenManager.saveToken(token)
                        _success.value = true
                    } else {
                        _error.value = "Token tidak ditemukan"
                    }
                } else {
                    _error.value = "Login gagal"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }

            _isLoading.value = false
        }
    }

    // ✅ INI YANG MEMPERBAIKI ERROR
    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = repository.register(username, email, password)
                if (response.isSuccessful) {
                    _success.value = true
                } else {
                    _error.value = "Register gagal"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }

            _isLoading.value = false
        }
    }

    fun resetState() {
        _success.value = false
        _error.value = null
    }
}
