package com.example.alp_vp_frontend.data.repository

import com.example.alp_vp_frontend.data.dto.LoginRequest
import com.example.alp_vp_frontend.data.dto.RegisterRequest
import com.example.alp_vp_frontend.data.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val api: AuthService
) {

    suspend fun login(email: String, password: String) =
        withContext(Dispatchers.IO) {
            api.login(LoginRequest(email, password))
        }

    suspend fun register(
        name: String,
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        api.register(
            RegisterRequest(
                username = name,
                email = email,
                password = password
            )
        )
    }
}
