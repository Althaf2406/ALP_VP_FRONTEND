package com.example.alp_vp_frontend.data.container

import android.content.Context
import com.example.alp_vp_frontend.data.local.AuthInterceptor
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.repository.UserRepository
import com.example.alp_vp_frontend.data.service.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserContainer(context: Context) {

    companion object {
        const val BASE_URL = "http://192.168.56.1:3002/"
    }

    // ============================
    // TOKEN MANAGER
    // ============================
    private val tokenManager = TokenManager(context)

    // ============================
    // OKHTTP CLIENT (WITH TOKEN)
    // ============================
    private val authClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenManager))
        .build()

    // ============================
    // PUBLIC RETROFIT (LOGIN / REGISTER)
    // ============================
    private val publicRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}api/public/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // ============================
    // PRIVATE RETROFIT (USER ME)
    // ============================
    private val privateRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}api/private/")
        .client(authClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // ============================
    // SERVICES
    // ============================
    private val publicUserService: UserService =
        publicRetrofit.create(UserService::class.java)

    private val privateUserService: UserService =
        privateRetrofit.create(UserService::class.java)

    // ============================
    // REPOSITORY
    // ============================
    val userRepository: UserRepository =
        UserRepository(
            publicApi = publicUserService,
            privateApi = privateUserService,
            tokenManager = tokenManager
        )
}
