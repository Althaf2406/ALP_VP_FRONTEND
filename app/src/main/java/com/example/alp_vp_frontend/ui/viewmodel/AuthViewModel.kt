package com.example.alp_vp_frontend.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.dto.LoginResponse
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.repository.AuthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class AuthViewModel(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success
    private val _loginResult = MutableStateFlow<LoginResponse?>(null)
    val loginResult: StateFlow<LoginResponse?> = _loginResult

    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> = _userId


    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = authRepository.login(email, password)

                if (response.isSuccessful) {
                    val body = response.body()!!

                    // 1️⃣ Simpan token
                    tokenManager.saveToken(body.data.token)

                    // 2️⃣ Simpan login result
                    _loginResult.value = body

                    // 🔥 3️⃣ WAJIB: restore userId dari token
                    restoreUserFromToken()
                    Log.d("AUTH_VM", "RESTORED userId = $userId")


                } else {
                    _error.value = "Login gagal (${response.code()})"
                }

            } catch (e: Exception) {
                _error.value = e.message ?: "Login error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = authRepository.register(username, email, password)
                if (response.isSuccessful) {
                    _success.value = true
                } else {
                    _error.value = "Register gagal"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ============================
    // LOGOUT
    // ============================
    fun logout() {
        authRepository.logout()


    }

    // ============================
    // CHECK LOGIN
    // ============================
    fun isLoggedIn(bool: Boolean): Boolean {
        return authRepository.isLoggedIn()
    }

    fun restoreUserFromToken() {
        val token = tokenManager.getToken() ?: return

        val userId = JwtUtils.getUserId(token)
        _userId.value = userId

        Log.d("AUTH_VM", "RESTORED userId = $userId")
    }

    fun resetState() {
        _success.value = false
        _error.value = null
    }
    object JwtUtils {
        fun getUserId(token: String): Int? {
            return try {
                val payload = token.split(".")[1]
                val decoded = String(
                    android.util.Base64.decode(payload, android.util.Base64.URL_SAFE)
                )
                val json = org.json.JSONObject(decoded)
                json.getInt("id")
            } catch (e: Exception) {
                null
            }
        }
    }
}



