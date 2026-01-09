package com.example.alp_vp_frontend.data.repository;

import androidx.datastore.preferences.protobuf.Any;

import com.example.alp_vp_frontend.data.dto.FocusListResponse;
import com.example.alp_vp_frontend.data.dto.FocusResponse;

import java.util.Map;

@ -1,91 +0,0 @@
package com.example.alp_vp_frontend.data.repository

import com.example.alp_vp_frontend.data.dto.Focus
import com.example.alp_vp_frontend.data.dto.FocusListResponse
import com.example.alp_vp_frontend.data.dto.FocusResponse
import com.example.alp_vp_frontend.data.service.FocusService

class FocusRepository(
    private val focusService: FocusService
) {

    // ============================
    // GET ALL FOCUS
    // ============================
    suspend fun getAllFocus(): Result<FocusListResponse> {
        return try {
            val response = focusService.getAllFocus()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to load focus list"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ============================
    // GET ONE FOCUS
    // ============================
    suspend fun getFocusById(id: Int): Result<FocusResponse> {
        return try {
            val response = focusService.getFocusById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Focus not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ============================
    // CREATE FOCUS
    // ============================
    suspend fun createFocus(focus: Focus): Result<FocusResponse> {
        return try {
            val response = focusService.createFocus(focus)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to create focus"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ============================
    // UPDATE FOCUS
    // ============================
    suspend fun updateFocus(id: Int, updates:Map<String, Any>): Result<FocusResponse> {
        return try {
            val response = focusService.updateFocus(id, updates)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to update focus"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ============================
    // DELETE FOCUS
    // ============================
    suspend fun deleteFocus(id: Int): Result<FocusResponse> {
        return try {
            val response = focusService.deleteFocus(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to delete focus"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}