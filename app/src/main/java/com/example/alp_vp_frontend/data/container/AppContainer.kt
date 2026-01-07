package com.example.alp_vp_frontend.data.container

import android.content.Context
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.local.AuthInterceptor
import com.example.alp_vp_frontend.data.repository.AuthRepository
import com.example.alp_vp_frontend.data.repository.FocusPhaseRepository
import com.example.alp_vp_frontend.data.repository.FocusRepository
import com.example.alp_vp_frontend.data.repository.MoneyRepository
import com.example.alp_vp_frontend.data.service.AuthService
import com.example.alp_vp_frontend.data.service.FocusPhaseService
import com.example.alp_vp_frontend.data.service.FocusService
import com.example.alp_vp_frontend.data.service.MoneyService
import com.example.alp_vp_frontend.data.service.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val tokenManager = TokenManager(context)
    companion object {
        const val BASE_URL = "http://192.168.88.23:3002/"
    }

    private val authClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenManager))
        .build()

    private val publicRetrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}api/public/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val privateRetrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}api/private/")
        .client(authClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val publicUserService: UserService =
        publicRetrofit.create(UserService::class.java)

    private val privateUserService: UserService =
        privateRetrofit.create(UserService::class.java)

    // ===== SERVICES =====
    val authService: AuthService =
        publicRetrofit.create(AuthService::class.java)

    val userService: UserService =
        privateRetrofit.create(UserService::class.java)

    val moneyService: MoneyService =
        privateRetrofit.create(MoneyService::class.java)

    val focusService: FocusService =
        privateRetrofit.create(FocusService::class.java)

    val focusPhaseService: FocusService =
        privateRetrofit.create(FocusService::class.java)

    // ===== REPOSITORIES =====
    val authRepository =
        AuthRepository(
            tokenManager = tokenManager,
            authService = authService,
            publicApi = publicUserService,
            privateApi = privateUserService
        )



    val moneyRepository =
        MoneyRepository(moneyService)

    val focusRepository =
        FocusRepository(focusService)
}

