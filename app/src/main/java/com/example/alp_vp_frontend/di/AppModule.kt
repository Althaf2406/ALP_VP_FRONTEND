//package com.example.alp_vp_frontend.di
//
//import com.example.alp_vp_frontend.data.repository.ItemRepository
//import com.example.alp_vp_frontend.data.repository.ItemRepositoryImpl
//import com.example.alp_vp_frontend.data.service.ItemApiService
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    // PASTIKAN IP INI BISA DIJANGKAU! (10.0.2.2 = Emulator)
//    private const val BASE_URL = "http://10.0.2.2:3000/api/"
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideItemApiService(retrofit: Retrofit): ItemApiService {
//        // Hilt membuat implementasi dari interface ini
//        return retrofit.create(ItemApiService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    // Hilt akan menginisialisasi ItemRepositoryImpl dan menyediakannya sebagai ItemRepository
//    fun provideItemRepository(impl: ItemRepositoryImpl): ItemRepository {
//        return impl
//    }
//}