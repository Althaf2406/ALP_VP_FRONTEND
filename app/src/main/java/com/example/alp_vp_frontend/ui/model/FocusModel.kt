package com.example.alp_vp_frontend.ui.model

import com.example.alp_vp_frontend.data.dto.Focus

data class FocusModel(
    val id: Int,
    val startTime: String,
    val endTime: String,
    val createdAt: String
) {
    companion object
}

// =============================
// SINGLE DTO → UI MODEL
// =============================
fun FocusModel.Companion.from(focus: Focus): FocusModel {
    return FocusModel(
        id = focus.id,
        startTime = focus.startTime,
        endTime = focus.endTime,
        createdAt = focus.createdAt
    )
}

// =============================
// LIST DTO → LIST UI MODEL
// =============================
fun FocusModel.Companion.fromList(apiList: List<Focus>): List<FocusModel> {
    return apiList.map { from(it) }
}
