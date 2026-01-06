package com.example.alp_vp_frontend.data.container

import android.content.Context
import com.example.alp_vp_frontend.data.local.AuthInterceptor
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.repository.FocusRepository
import com.example.alp_vp_frontend.data.service.FocusService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FocusContainer(context: Context) {

    companion object {
        const val BASE_URL = "http://192.168.56.1:3002/"
    }

    // Token Manager
    private val tokenManager = TokenManager(context)

    // OkHttp with Authorization Bearer
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenManager))
        .build()

    // PRIVATE Retrofit
    private val privateRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}api/private/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Service
    val focusService: FocusService =
        privateRetrofit.create(FocusService::class.java)

    // Repository
    val focusRepository: FocusRepository =
        FocusRepository(focusService)
}
