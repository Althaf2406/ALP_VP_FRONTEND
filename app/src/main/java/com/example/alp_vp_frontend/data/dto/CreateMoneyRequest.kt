package com.example.alp_vp_frontend.data.dto

data class CreateMoneyRequest(
    val title: String,
    val description: String,
    val amount: String,
    val type: String
)
