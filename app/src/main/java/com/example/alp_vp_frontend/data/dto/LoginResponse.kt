package com.example.alp_vp_frontend.data.dto

data class LoginResponse(
    val data: TokenData
)

data class TokenData(
    val token: String?,
    val id: Int?,
    val username: String?,
    val email: String?
)
