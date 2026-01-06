package com.example.alp_vp_frontend.data.repository

import com.example.alp_vp_frontend.data.dto.Phase
import com.example.alp_vp_frontend.data.service.FocusPhaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FocusPhaseRepository(
    private val api: FocusPhaseService
) {

    // ============================
    // GET PHASES BY FOCUS
    // ============================
    suspend fun getByFocusId(focusId: Int): List<Phase> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getByFocusId(focusId)
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }

    // ============================
    // CREATE PHASE
    // ============================
    suspend fun createPhase(
        focusId: Int,
        type: String,
        duration: Int
    ): Phase? = withContext(Dispatchers.IO) {
        try {
            val body = mapOf(
                "focus_id" to focusId,
                "type" to type,
                "duration" to duration
            )
            val response = api.create(body)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ============================
    // UPDATE PHASE
    // ============================
    suspend fun updatePhase(
        id: Int,
        updates: Map<String, Any>
    ): Phase? = withContext(Dispatchers.IO) {
        try {
            val response = api.update(id, updates)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ============================
    // DELETE PHASE
    // ============================
    suspend fun deletePhase(id: Int): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val response = api.delete(id)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
}
