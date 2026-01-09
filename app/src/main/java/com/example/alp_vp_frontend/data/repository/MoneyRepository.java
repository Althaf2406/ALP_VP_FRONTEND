package com.example.alp_vp_frontend.data.repository

import com.example.alp_vp_frontend.data.dto.CreateMoneyRequest
import com.example.alp_vp_frontend.data.dto.Money
import com.example.alp_vp_frontend.data.dto.UpdateMoneyRequest
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
    suspend fun createMoney(
        request: CreateMoneyRequest
    ) = withContext(Dispatchers.IO) {
        privateApi.createMoney(request)
    }


    // ==============================
    // UPDATE MONEY
    // ==============================
    suspend fun updateMoney(
        id: Int,
        request: UpdateMoneyRequest
    ) = withContext(Dispatchers.IO) {
        try {
            privateApi.updateMoney(id, request)
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
