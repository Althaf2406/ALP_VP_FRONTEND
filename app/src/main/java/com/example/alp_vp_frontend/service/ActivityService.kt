package com.example.alp_vp_frontend.data.service

import com.example.alp_vp_frontend.data.dto.AllActivityResponse
import com.example.alp_vp_frontend.data.dto.Data
import com.example.alp_vp_frontend.data.dto.FocusResponse
import com.example.alp_vp_frontend.data.dto.MoneyResponse
import com.example.alp_vp_frontend.data.dto.NewActivity
import com.example.alp_vp_frontend.data.dto.ResponsActivityById
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityService {

    @GET("actAll")
    suspend fun GetAllActivity(

    ): Response<AllActivityResponse>

    @GET("activity/{id}")
    suspend fun getActivityById(
        @Path("id") id: Int
    ): Response<ResponsActivityById>

    @POST("activity")
    suspend fun createActivity(
        @Body body: NewActivity
    )

    @PATCH("activity/{id}")
    suspend fun updateActivity(
        @Path("id") id: Int,
        @Body updates: Map<String, Any>
    )

    @DELETE("activity/{id}")
    suspend fun deleteFocus(
        @Path("id") id: Int
    )
}