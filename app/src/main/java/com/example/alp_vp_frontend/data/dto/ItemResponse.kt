package com.example.alp_vp_frontend.data.dto

import com.example.alp_vp_frontend.data.model.Item
import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("dateTime") val dateTime: String?, // Waktu utama (Task deadline, Activity start)
    @SerializedName("startDateTime") val startDateTime: String?, // Spesifik Activity
    @SerializedName("endDateTime") val endDateTime: String?, // Spesifik Activity
    @SerializedName("isCompleted") val isCompleted: Boolean,
    @SerializedName("difficulty") val difficulty: String?,
    @SerializedName("createdAt") val createdAt: String,
)

fun ItemResponse.toDomain(): Item {
    return Item(
        id = this.id,
        type = this.type,
        title = this.title,
        description = this.description,
        dateTime = this.dateTime,
        startDateTime = this.startDateTime,
        endDateTime = this.endDateTime,
        isCompleted = this.isCompleted,
        difficulty = this.difficulty,
        createdAt = this.createdAt
    )
}