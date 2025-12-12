package com.example.alp_vp_frontend.data.container

import com.example.alp_vp_frontend.data.repository.FocusRepository
import com.example.alp_vp_frontend.data.service.FocusService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FocusContainer {

    private val BASE_URL = "http://10.0.2.2:3002/api/"   // emulator to localhost

    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Service
    val focusService: FocusService = retrofit.create(FocusService::class.java)

    // Repository
    val focusRepository: FocusRepository = FocusRepository(focusService)
}
