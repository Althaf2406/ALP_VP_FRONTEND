package com.example.alp_vp_frontend.data.service

import com.example.alp_vp_frontend.data.dto.Phase
import retrofit2.Response
import retrofit2.http.*

interface FocusPhaseService {

    // ============================
    // GET ALL PHASE
    // ============================
    @GET("focusphase")
    suspend fun getAll(): Response<List<Phase>>

    // ============================
    // GET PHASE BY ID
    // ============================
    @GET("focusphase/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): Response<Phase>

    // ============================
    // GET PHASE BY FOCUS ID
    // ============================
    @GET("focusphase/focus/{focus_id}")
    suspend fun getByFocusId(
        @Path("focus_id") focusId: Int
    ): Response<List<Phase>>

    // ============================
    // CREATE PHASE
    // ============================
    @POST("focusphase")
    suspend fun create(
        @Body body: Map<String, Any>
    ): Response<Phase>

    // ============================
    // UPDATE PHASE
    // ============================
    @PATCH("focusphase/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body updates: Map<String, Any>
    ): Response<Phase>

    // ============================
    // DELETE PHASE
    // ============================
    @DELETE("focusphase/{id}")
    suspend fun delete(
        @Path("id") id: Int
    ): Response<Unit>
}
