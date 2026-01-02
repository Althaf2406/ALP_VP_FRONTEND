package com.example.alp_vp_frontend.data.container

import com.example.alp_vp_frontend.data.service.ActivityService
import com.example.alp_vp_frontend.data.repository.ActivityRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

class ActivityContainer {

    companion object {
        val BASE_URL = "http://10.0.2.2:3000/"

    }

    private val client = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(com.example.alp_vp_frontend.data.container.ActivityContainer.Companion.BASE_URL)
        .client(client)
        .build()

    private val retrofitService: ActivityService by lazy {
        retrofit.create(ActivityService::class.java)
    }

    val ActivityRepository: ActivityRepository by lazy {
        ActivityRepository(retrofitService)
    }
}