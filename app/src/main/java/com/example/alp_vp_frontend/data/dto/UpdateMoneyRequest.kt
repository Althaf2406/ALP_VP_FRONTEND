package com.example.alp_vp_frontend.data.dto

data class UpdateMoneyRequest(
    val title: String,
    val description: String,
    val amount: Int,
    val type: String
)
