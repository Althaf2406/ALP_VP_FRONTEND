package com.example.alp_vp_frontend.data.repository

import com.example.alp_vp_frontend.data.dto.LoginRequest
import com.example.alp_vp_frontend.data.dto.LoginResponse
import com.example.alp_vp_frontend.data.dto.RegisterRequest
import com.example.alp_vp_frontend.data.dto.User
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val publicApi: UserService,
    private val privateApi: UserService,
    private val tokenManager: TokenManager
) {

    // =======================================================
    // LOGIN
    // =======================================================
    suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = publicApi.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (!token.isNullOrBlank()) {
                        tokenManager.saveToken(token)
                        true
                    } else false
                } else {
                    println("❌ Login error: ${response.code()} ${response.message()}")
                    false
                }

            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }


    // =======================================================
    // REGISTER
    // =======================================================
    suspend fun register(request: RegisterRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = publicApi.register(request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }


    // =======================================================
    // GET CURRENT USER
    // =======================================================
    suspend fun getMe(): User? {
        return withContext(Dispatchers.IO) {
            try {
                val response = privateApi.getCurrentUser()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    println("❌ getMe error: ${response.code()} ${response.message()}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }


    // =======================================================
    // LOGOUT
    // =======================================================
    fun logout() {
        tokenManager.clearToken()
    }


    // =======================================================
    // CHECK LOGIN
    // =======================================================
    fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }
}
