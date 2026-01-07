package com.example.alp_vp_frontend.data.dto

data class CreateMoneyRequest(
    val title: String,
    val description: String,
    val amount: Int,
    val type: String,
    val user_id: Int
)
