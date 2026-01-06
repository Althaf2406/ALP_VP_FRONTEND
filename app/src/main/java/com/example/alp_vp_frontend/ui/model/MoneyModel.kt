package com.example.alp_vp_frontend.ui.model

import com.example.alp_vp_frontend.data.dto.Money

data class MoneyModel(
    val id: Int,
    val title: String,
    val description: String,
    val amount: Int,
    val type: String,        // Income / Outcome
    val createdAt: String    // akan diformat di UI kalau perlu
) {
    companion object
}

// =============================
// SINGLE DTO → UI MODEL
// =============================
fun MoneyModel.Companion.from(money: Money): MoneyModel {
    return MoneyModel(
        id = money.id,
        title = money.title,
        description = money.description,
        amount = money.amount,
        type = money.type,
        createdAt = money.createdAt
    )
}

// =============================
// LIST DTO → LIST UI MODEL
// =============================
fun MoneyModel.Companion.fromList(apiList: List<Money>): List<MoneyModel> {
    return apiList.map { from(it) }
}