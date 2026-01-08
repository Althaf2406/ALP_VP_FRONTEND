package com.example.alp_vp_frontend.data.dto

data class Focus(
    val createdAt: String,
    val endTime: String,
    val id: Int,
    val phases: List<Phase>,
    val startTime: String,
    val user_id: Int
)