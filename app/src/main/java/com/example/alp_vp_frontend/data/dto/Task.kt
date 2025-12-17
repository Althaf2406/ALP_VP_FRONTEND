package com.example.alp_vp_frontend.data.dto
import com.example.alp_vp_frontend.data.model.Task

// --- data/remote/model/request/CreateTaskRequest.kt ---
import com.google.gson.annotations.SerializedName
data class CreateTaskRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("dateTime") val dateTime: String,
    @SerializedName("difficulty") val difficulty: String
)

data class TaskResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("dateTime") val dateTime: String?,
    @SerializedName("isCompleted") val isCompleted: Boolean,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)
fun TaskResponse.toDomain(): Task {
    return Task(
        id = this.id, title = this.title, description = this.description,
        dateTime = this.dateTime ?: "", isCompleted = this.isCompleted,
        difficulty = this.difficulty, createdAt = this.createdAt, updatedAt = this.updatedAt
    )
}
// ... (Lainnya: ActivityResponse, SummaryResponse, ItemResponse)