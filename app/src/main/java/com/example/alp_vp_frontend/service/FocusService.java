package com.example.alp_vp_frontend.service;

import androidx.datastore.preferences.protobuf.Any;

import com.example.alp_vp_frontend.data.dto.FocusListResponse;
import com.example.alp_vp_frontend.data.dto.FocusResponse;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

@ -1,49 +0,0 @@
package com.example.alp_vp_frontend.data.service

import com.example.alp_vp_frontend.data.dto.Focus
import com.example.alp_vp_frontend.data.dto.FocusResponse
import com.example.alp_vp_frontend.data.dto.FocusListResponse
import retrofit2.Response
import retrofit2.http.*

interface FocusService {

    // ============================
    // GET ALL FOCUS
    // ============================
    @GET("focus")
    suspend fun getAllFocus(): Response<FocusListResponse>

    // ============================
    // GET ONE FOCUS
    // ============================
    @GET("focus/{id}")
    suspend fun getFocusById(
            @Path("id")id: Int
    ): Response<FocusResponse>

    // ============================
    // CREATE FOCUS
    // ============================
    @POST("focus")
    suspend fun createFocus(
            @Body body: Focus
    ): Response<FocusResponse>

    // ============================
    // UPDATE FOCUS
    // ============================
    @PATCH("focus/{id}")
    suspend fun updateFocus(
        @Path("id") id: Int,
        @Body updates:Map<String, Any>
    ): Response<FocusResponse>

    // ============================
    // DELETE FOCUS
    // ============================
    @DELETE("focus/{id}")
    suspend fun deleteFocus(
        @Path("id") id: Int
    ): Response<FocusResponse>
}