package com.example.alp_vp_frontend.di

import com.example.alp_vp_frontend.data.repository.ItemRepositoryImpl
import com.example.alp_vp_frontend.data.service.ItemApiService
import com.example.alp_vp_frontend.ui.viewmodel.ItemViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// File: data/di/AppContainer.kt atau AppContainer.kt

interface AppContainer {
    val itemRepository: ItemRepositoryImpl
    val viewModelFactory: ItemViewModelFactory
}

class DefaultAppContainer : AppContainer {

    private val BASE_URL = "http://10.0.2.2:3000/api/"


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itemApiService: ItemApiService by lazy {
        retrofit.create(ItemApiService::class.java)
    }

    override val itemRepository: ItemRepositoryImpl by lazy {
        ItemRepositoryImpl(itemApiService)
    }

    override val viewModelFactory: ItemViewModelFactory by lazy {
        ItemViewModelFactory(itemRepository)
    }
}