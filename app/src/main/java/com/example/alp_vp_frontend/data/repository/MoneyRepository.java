package com.example.alp_vp_frontend.data.repository;

import com.example.alp_vp_frontend.data.dto.Money;

import kotlinx.coroutines.Dispatchers;

@ -1,78 +0,0 @@
package com.example.alp_vp_frontend.data.repository

import com.example.alp_vp_frontend.data.dto.Money
import com.example.alp_vp_frontend.data.service.MoneyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoneyRepository(
    private val privateApi: MoneyService
) {

    // ==============================
    // GET ALL MONEY
    // ==============================
    suspend fun getAllMoney() = withContext(Dispatchers.IO) {
        try {
            privateApi.getAllMoney()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ==============================
    // GET ONE MONEY
    // ==============================
    suspend fun getMoneyById(id: Int) = withContext(Dispatchers.IO) {
        try {
            privateApi.getMoneyById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ==============================
    // CREATE MONEY
    // ==============================
    suspend fun createMoney(body:Money) = withContext(Dispatchers.IO) {
        try {
            privateApi.createMoney(body)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ==============================
    // UPDATE MONEY
    // ==============================
    suspend fun updateMoney(id: Int, body: Money) = withContext(Dispatchers.IO) {
        try {
            val map = mutableMapOf<String, Any>()

            body.title?.let { map["title"] = it }
            body.description?.let { map["description"] = it }
            body.amount?.let { map["amount"] = it }
            body.type?.let { map["type"] = it }

            privateApi.updateMoney(id, map)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ==============================
    // DELETE MONEY
    // ==============================
    suspend fun deleteMoney(id: Int) = withContext(Dispatchers.IO) {
        try {
            privateApi.deleteMoney(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}