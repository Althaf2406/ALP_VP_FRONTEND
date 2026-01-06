package com.example.alp_vp_frontend.data.container

import android.content.Context
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.local.AuthInterceptor
import com.example.alp_vp_frontend.data.repository.UserRepository
import com.example.alp_vp_frontend.data.service.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    companion object {
        const val BASE_URL = "http://192.168.56.1:3002/" // Emulator ke backend NodeJS
    }

    private val tokenManager = TokenManager(context)

    // ================
    // OKHTTP for PRIVATE ENDPOINT (with Token)
    // ================
    private val authClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenManager))
        .build()

    // ================
    // PUBLIC RETROFIT (NO TOKEN)
    // ================
    private val publicRetrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}api/public/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val publicUserService: UserService =
        publicRetrofit.create(UserService::class.java)

    // ================
    // PRIVATE RETROFIT (WITH TOKEN)
    // ================
    private val privateRetrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}api/private/")
        .client(authClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val privateUserService: UserService =
        privateRetrofit.create(UserService::class.java)

    // ================
    // REPOSITORY
    // ================
    val userRepository: UserRepository =
        UserRepository(publicUserService, privateUserService, tokenManager)
}
