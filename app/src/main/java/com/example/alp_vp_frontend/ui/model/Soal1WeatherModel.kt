//package com.example.labweek7.ui.model
//
//data class WeatherDisplay(
//    val cod: Int,
//    val name: String?,
//    val sys: Sys?,
//    val clouds: Clouds?,
//    val wind: Wind?,
//    val main: Main?,
//    val weather: List<Weather>?,
//    val rain: Rain? = null
//){
//    companion object
//}
//
//data class Weather(
//    val main: String,
//    val icon: String,
//    val description: String
//)
//
//data class Rain(
//    val oneHour: Double
//)
//data class Main(
//    val temp: Double,
//    val pressure: Int,
//    val humidity: Int,
//    val feels_like: Double
//)
//
//data class Wind(
//    val speed: Double
//)
//
//data class Clouds(
//    val all: Int
//)
//
//data class Sys(
//    val sunset: Int,
//    val sunrise: Int
//)
//
//fun WeatherDisplay.Companion.from(api: WeatherApp): WeatherDisplay {
//    return WeatherDisplay(
//        cod = api.cod,
//        name = api.name,
//        sys = api.sys?.let { Sys(it.sunset, it.sunrise) },
//        clouds = api.clouds?.let { Clouds(it.all) },
//        wind = api.wind?.let { Wind(it.speed) },
//        main = api.main?.let { Main(it.temp, it.pressure, it.humidity, it.feels_like) },
//        weather = api.weather?.map {
//            Weather(
//                main = it.main,
//                icon = it.icon,
//                description = it.description
//            )
//        },
//        rain = api.rain?.let { Rain(it.oneHour ?: 0.0) }
//    )
//}