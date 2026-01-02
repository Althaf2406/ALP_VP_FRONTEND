package com.example.alp_vp_frontend.data.repository

import android.util.Log
import com.example.alp_vp_frontend.data.dto.AllActivityResponse
import com.example.alp_vp_frontend.data.dto.NewActivity
import com.example.alp_vp_frontend.data.dto.ResponsActivityById
import com.example.alp_vp_frontend.data.service.ActivityService
import java.io.IOException

class ActivityRepository(
    private val service: ActivityService
) {


    suspend fun getAllActivities(): AllActivityResponse? {
        return try {
            val response = service.GetAllActivity()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("ActivityRepo", "Error getting activities: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ActivityRepo", "Exception getting activities", e)
            null
        }
    }


    suspend fun getActivityById(id: Int): ResponsActivityById? {
        return try {
            val response = service.getActivityById(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("ActivityRepo", "Error getting activity by id: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ActivityRepo", "Exception getting activity by id", e)
            null
        }
    }


    suspend fun createActivity(newActivity: NewActivity): Boolean {
        return try {
            service.createActivity(newActivity)

            true
        } catch (e: Exception) {
            Log.e("ActivityRepo", "Exception creating activity", e)
            false
        }
    }


    suspend fun updateActivity(id: Int, updates: Map<String, Any>): Boolean {
        return try {
            service.updateActivity(id, updates)
            true
        } catch (e: Exception) {
            Log.e("ActivityRepo", "Exception updating activity", e)
            false
        }
    }


    suspend fun deleteActivity(id: Int): Boolean {
        return try {
            service.deleteFocus(id)
            true
        } catch (e: Exception) {
            Log.e("ActivityRepo", "Exception deleting activity", e)
            false
        }
    }
}
