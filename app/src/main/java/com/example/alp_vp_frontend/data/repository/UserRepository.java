package com.example.alp_vp_frontend.data.repository;

import com.example.alp_vp_frontend.data.local.TokenManager;

import kotlinx.coroutines.Dispatchers;

@ -1,96 +0,0 @@
package com.example.alp_vp_frontend.data.repository

import com.example.alp_vp_frontend.data.dto.LoginRequest
import com.example.alp_vp_frontend.data.dto.RegisterRequest
import com.example.alp_vp_frontend.data.dto.User
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.service.AuthService
import com.example.alp_vp_frontend.data.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val publicApi: UserService,
    private val privateApi: UserService,
<<<<<<<< HEAD:app/src/main/java/com/example/alp_vp_frontend/data/repository/AuthRepository.kt
    private val authService: AuthService,
    private val tokenManager: TokenManager
========
    private val tokenManager:TokenManager
>>>>>>>> Gregory:app/src/main/java/com/example/alp_vp_frontend/data/repository/UserRepository.java
) {

    suspend fun login(email: String, password: String) =
        withContext(Dispatchers.IO) {
            val response = authService.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                response.body()?.data?.token.let { token ->
                    tokenManager.saveToken(token)
                }
            }

            response
        }

    suspend fun register(
        username: String,
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        authService.register(
            RegisterRequest(
                username = username,
                email = email,
                password = password
            )
        )
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
<<<<<<<< HEAD:app/src/main/java/com/example/alp_vp_frontend/data/repository/AuthRepository.kt
}

========
}
>>>>>>>> Gregory:app/src/main/java/com/example/alp_vp_frontend/data/repository/UserRepository.java
