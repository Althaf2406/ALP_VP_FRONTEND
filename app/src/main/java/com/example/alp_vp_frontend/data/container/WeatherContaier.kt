package com.example.labweek7.data.container

import com.example.labweek7.data.repository.WeatherAppServerRepository
import com.example.labweek7.data.service.WeaherAppServerService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherContainer {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "2639266dc96f22afed5d32ceff58ead4"
        val BASE_IMG_URL = "https://openweathermap.org/img/wn/"

    }

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: WeaherAppServerService by lazy {
        retrofit.create(WeaherAppServerService::class.java)
    }

    val repository : WeatherAppServerRepository by lazy {
        WeatherAppServerRepository(retrofitService)
    }
}
