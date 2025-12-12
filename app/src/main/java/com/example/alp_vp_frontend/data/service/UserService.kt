package com.example.alp_vp_frontend.data.service

import com.example.alp_vp_frontend.data.dto.LoginRequest
import com.example.alp_vp_frontend.data.dto.RegisterRequest
import com.example.alp_vp_frontend.data.dto.LoginResponse
import com.example.alp_vp_frontend.data.dto.User
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    // =============================
    // REGISTER
    // =============================
    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): Response<User>

    // =============================
    // LOGIN
    // =============================
    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>

    // =============================
    // GET CURRENT USER
    // =============================
    @GET("user/me")
    suspend fun getCurrentUser(): Response<User>

    // =============================
    // UPDATE USER
    // =============================
    @PATCH("user/update")
    suspend fun updateUser(
        @Body updates: Map<String, Any>
    ): Response<User>
}
