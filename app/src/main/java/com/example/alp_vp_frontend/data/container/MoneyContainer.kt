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
        const val BASE_URL = "http://10.0.2.2:3002/"
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


    // Services
//    val publicActivityService: MoneyService by lazy {
//        publicRetrofit.create(MoneyService::class.java)
//    }

    val privateMoneyService: MoneyService by lazy {
        privateRetrofit.create(MoneyService::class.java)
    }

    // Repository
//    val moneyRepository: MoneyRepository by lazy {
//        MoneyRepository(
//            publicApi = publicMoneyService,
//            privateApi = privateMoneyService
//        )
//    }
}
