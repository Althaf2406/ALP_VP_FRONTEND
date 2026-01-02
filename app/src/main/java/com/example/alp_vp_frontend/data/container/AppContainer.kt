package com.example.alp_vp_frontend.data.container

import com.example.alp_vp_frontend.data.repository.ActivityRepository
import com.example.alp_vp_frontend.data.repository.UserRepository
import com.example.alp_vp_frontend.data.service.ActivityService
// import com.example.alp_vp_frontend.data.service.UserService // Untuk Login nanti
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AppContainer {


    private val baseUrl = "http://10.0.2.2:3000/"

    // Membuat mesin koneksi internet (Retrofit)
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    // ==================
    //  SERVICES
    // ==================

    // Membuat "Telepon" untuk berbicara dengan API Activity
    private val activityService: ActivityService by lazy {
        retrofit.create(ActivityService::class.java)
    }

    // Nanti, kita akan buat "Telepon" untuk Login di sini
    // private val userService: UserService by lazy { ... }


    // ==================
    //  REPOSITORIES
    // ==================

    // MEMBUAT "MESIN" ActivityRepository dengan cara yang benar
    val activityRepository: ActivityRepository by lazy {
        ActivityRepository(activityService)
    }

    // Karena Login belum dikerjakan, UserRepository kita buat "kosongan"
    // Ini mungkin masih error jika UserRepository Anda butuh UserService,
    // tapi kita akan fokus pada Activity dulu.
    // Jika ini error, kita akan buat UserService palsu.
    val userRepository: UserRepository by lazy {
        // Untuk sementara, kita tidak akan menggunakannya.
        // Baris ini akan kita perbaiki saat mengerjakan fitur Login.
        // Kita tidak bisa memberikan 'null' jika constructor tidak memperbolehkannya.
        // Jadi kita tinggalkan ini untuk nanti.
        TODO("Create UserService and then initialize UserRepository")
    }
}
