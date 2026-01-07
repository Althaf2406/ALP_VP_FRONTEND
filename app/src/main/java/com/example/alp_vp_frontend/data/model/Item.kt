package com.example.alp_vp_frontend.data.model

// Digunakan untuk endpoint /api/all
data class Item(
    val id: String,
    val type: String, // 'Task' atau 'Activity'
    val title: String,
    val description: String?,
    val dateTime: String?, // Waktu utama
    val startDateTime: String?,
    val endDateTime: String?,
    val isCompleted: Boolean,
    val difficulty: String?,
    val createdAt: String,
)

// Digunakan untuk endpoint /api/summary
data class Summary(
    val todayAlmostDonePercentage: Int
)