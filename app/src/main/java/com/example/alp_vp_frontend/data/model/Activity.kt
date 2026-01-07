package com.example.alp_vp_frontend.data.model

import com.google.gson.annotations.SerializedName

data class Activity(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("startDateTime") val startDateTime: String, // WAJIB
    @SerializedName("endDateTime") val endDateTime: String,     // WAJIB
    @SerializedName("isCompleted") val isCompleted: Boolean? = false,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null
)
// Model ini digunakan untuk Create, Update, dan Response.