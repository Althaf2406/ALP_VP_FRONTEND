package com.example.alp_vp_frontend.data.container

import android.content.Context
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.repository.AuthRepository
import com.example.alp_vp_frontend.data.service.AuthService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthContainer(context: Context) {

    companion object {
        const val BASE_URL = "http://192.168.56.1:3002/"
        init {
            android.util.Log.d(
                "AUTH_CONTAINER",
                "BASE_URL = ${BASE_URL}api/public/"
            )
        }

    }

    private val okHttp = OkHttpClient.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}api/public/")
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepository(authService)
    }
}
