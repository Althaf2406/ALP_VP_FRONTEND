package com.example.labweek7.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.labweek7.ui.model.Rain
import com.example.labweek7.ui.model.Weather
import com.example.labweek7.ui.model.WeatherDisplay
import com.example.labweek8soal1.ui.view.Soal1SunriseSunsetCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Soal1WeatherResultCard(
    dataWDisplay: WeatherDisplay,
    dataWeather: Weather
) {
    val date = SimpleDateFormat("MMMM dd", Locale.getDefault()).format(Date())

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Soal1WeatherHeaderCard(
            city = dataWDisplay.name ?: "Unknown",
            date = date,
            updatedTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
        )

        Spacer(modifier = Modifier.height(24.dp))

        Soal1MainWeatherCard(
            condition = dataWDisplay.weather?.firstOrNull()?.main ?: "Unknown",
            temperature = dataWDisplay.main?.temp ?: 0.0,
            dataWDisplay = dataWDisplay,
            dataWeather = dataWeather
        )

        Spacer(modifier = Modifier.height(24.dp))

        Soal1WeatherInfoCard(
            main = dataWDisplay.main!!,
            windSpeed = dataWDisplay.wind!!,
            clouds = dataWDisplay.clouds!!,
            rain = dataWDisplay.rain ?: Rain(0.0)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Soal1SunriseSunsetCard(
            sunriseTime = dataWDisplay.sys?.sunrise?.toLong() ?: 0,
            sunsetTime = dataWDisplay.sys?.sunset?.toLong() ?: 0
        )

    }
}