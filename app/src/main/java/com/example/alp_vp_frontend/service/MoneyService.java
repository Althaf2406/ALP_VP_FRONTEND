package com.example.alp_vp_frontend.service;

import androidx.datastore.preferences.protobuf.Any;

import com.example.alp_vp_frontend.data.dto.Money;
import com.example.alp_vp_frontend.data.dto.MoneyListResponse;
import com.example.alp_vp_frontend.data.dto.MoneyResponse;

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

import com.example.alp_vp_frontend.data.dto.Money
import com.example.alp_vp_frontend.data.dto.MoneyResponse
import com.example.alp_vp_frontend.data.dto.MoneyListResponse
import retrofit2.Response
import retrofit2.http.*

interface MoneyService {

    // ============================
    // GET ALL MONEY
    // ============================
    @GET("money")
    suspend fun getAllMoney(): Response<MoneyListResponse>

    // =============================
    // GET ONE MONEY
    // =============================
    @GET("money/{id}")
    suspend fun getMoneyById(
            @Path("id")id: Int
    ): Response<MoneyResponse>

    // =============================
    // CREATE MONEY
    // =============================
    @POST("money")
    suspend fun createMoney(
            @Body body:Money
    ): Response<MoneyResponse>

    // =============================
    // UPDATE MONEY
    // =============================
    @PATCH("money/{id}")
    suspend fun updateMoney(
            @Path("id")id: Int,
            @Body updates:Map<String, Any>   // fleksibel
    ): Response<MoneyResponse>

    // =============================
    // DELETE MONEY
    // =============================
    @DELETE("money/{id}")
    suspend fun deleteMoney(
        @Path("id") id: Int
    ): Response<MoneyResponse>
}