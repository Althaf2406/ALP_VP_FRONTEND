package com.example.alp_vp_frontend.data.service

import com.example.alp_vp_frontend.data.dto.CreateMoneyRequest
import com.example.alp_vp_frontend.data.dto.Money
import com.example.alp_vp_frontend.data.dto.MoneyListResponse
import com.example.alp_vp_frontend.data.dto.MoneyResponse
import com.example.alp_vp_frontend.data.dto.UpdateMoneyRequest
import retrofit2.Response
import retrofit2.http.*

interface MoneyService {

    @GET("money")
    suspend fun getAllMoney(): Response<MoneyListResponse>

    @GET("money/{id}")
    suspend fun getMoneyById(
        @Path("id") id: Int
    ): Response<MoneyResponse>

    @POST("money")
    suspend fun createMoney(
        @Body request: CreateMoneyRequest
    ): Response<MoneyResponse>


    @PATCH("money/{id}")
    suspend fun updateMoney(
        @Path("id") id: Int,
        @Body request: UpdateMoneyRequest
    ): Response<MoneyResponse>


    @DELETE("money/{id}")
    suspend fun deleteMoney(
        @Path("id") id: Int
    ): Response<Unit>
}
