//package com.example.labweek7.data.repository
//
//import com.example.labweek7.data.service.WeaherAppServerService
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import
//
//
//class WeatherAppServerRepository(
//    private val service: WeaherAppServerService
//) {
//    suspend fun getWeather(city: String, apiKey: String, units: String = "metric"): WeatherApp? {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = service.getWeather(city, apiKey, units)
//                if (response.isSuccessful) {
//                    response.body()
//                } else {
//                    println("❌ Error: ${response.code()} ${response.message()}")
//                    null
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                null
//            }
//        }
//    }
//
//
//}
