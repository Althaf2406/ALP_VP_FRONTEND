package com.example.alp_vp_frontend.data.container

import android.content.Context
import com.example.alp_vp_frontend.data.local.AuthInterceptor
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.repository.MoneyRepository
import com.example.alp_vp_frontend.data.service.MoneyService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoneyContainer(context: Context) {

    companion object {
        const val BASE_URL = "http://192.168.56.1:3002/"
    }

    private val tokenManager = TokenManager(context)

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenManager))  // auto inject token
        .build()

    // 🔐 Private API (yang butuh login)
    private val privateRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}api/private/")
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //Services
    val privateMoneyService: MoneyService by lazy {
        privateRetrofit.create(MoneyService::class.java)
    }

    // Repository
    val moneyRepository: MoneyRepository by lazy {
        MoneyRepository(
            privateApi = privateMoneyService
        )
    }
}
