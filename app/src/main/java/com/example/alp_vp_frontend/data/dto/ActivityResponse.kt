package com.example.alp_vp_frontend.data.dto

import com.example.alp_vp_frontend.data.model.Activity
import com.google.gson.annotations.SerializedName

data class ActivityResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("startDateTime") val startDateTime: String?, 
    @SerializedName("endDateTime") val endDateTime: String?,   
    @SerializedName("isCompleted") val isCompleted: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)

fun ActivityResponse.toDomain(): Activity {
    return Activity(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.startDateTime ?: "", 
        endDateTime = this.endDateTime ?: "",     
        isCompleted = this.isCompleted,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}