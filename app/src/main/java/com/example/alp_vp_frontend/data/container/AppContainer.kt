package com.example.alp_vp_frontend.data.container

import android.content.Context
import com.example.alp_vp_frontend.data.local.TokenManager
import com.example.alp_vp_frontend.data.local.AuthInterceptor
import com.example.alp_vp_frontend.data.repository.ActivityRepository
import com.example.alp_vp_frontend.data.repository.AuthRepository
import com.example.alp_vp_frontend.data.repository.FocusPhaseRepository
import com.example.alp_vp_frontend.data.repository.FocusRepository
import com.example.alp_vp_frontend.data.repository.MoneyRepository
import com.example.alp_vp_frontend.data.service.ActivityService
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

    // keep user services for AuthRepository but do not expose a separate userRepository
    private val publicUserService: UserService =
        publicRetrofit.create(UserService::class.java)

    private val privateUserService: UserService =
        privateRetrofit.create(UserService::class.java)

    // ===== SERVICES =====
    val authService: AuthService =
        publicRetrofit.create(AuthService::class.java)

    val moneyService: MoneyService =
        privateRetrofit.create(MoneyService::class.java)

    val focusService: FocusService =
        privateRetrofit.create(FocusService::class.java)

    val focusPhaseService: FocusPhaseService =
        privateRetrofit.create(FocusPhaseService::class.java)

    private val activityService: ActivityService =
        privateRetrofit.create(ActivityService::class.java)

    // ===== REPOSITORIES =====
    val authRepository =
        AuthRepository(
            publicApi = publicUserService,
            privateApi = privateUserService,
            authService = authService,
            tokenManager = tokenManager
        )

    val moneyRepository =
        MoneyRepository(moneyService)

    val focusRepository =
        FocusRepository(focusService)

    val focusPhaseRepository =
        FocusPhaseRepository(focusPhaseService)

    val activityRepository =
        ActivityRepository(activityService)

    val userRepository =
        authRepository
    // keep compatibility with existing ViewModelFactory reference name
}
