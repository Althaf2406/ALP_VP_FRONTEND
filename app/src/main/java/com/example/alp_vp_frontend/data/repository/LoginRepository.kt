package com.example.alp_vp_frontend.data.repository

import com.example.alp_vp_frontend.data.dto.LoginRequest
import com.example.alp_vp_frontend.data.dto.LoginResponse
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.service.AuthService

class LoginRepository(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) {

    suspend fun login(email: String, password: String): LoginResponse {

        val response = authService.login(
            LoginRequest(email, password)
        )

        if (response.isSuccessful) {
            val body = response.body()
                ?: throw Exception("Empty response body")

            val token = body.data.token
            tokenManager.saveToken(token)

            return body
        } else {
            throw Exception(response.errorBody()?.string() ?: "Login failed")
        }
    }
}
