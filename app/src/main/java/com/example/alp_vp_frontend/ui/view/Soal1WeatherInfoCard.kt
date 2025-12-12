package com.example.labweek7.ui.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.labweek7.R
import com.example.labweek7.ui.model.Clouds
import com.example.labweek7.ui.model.Main
import com.example.labweek7.ui.model.Rain
import com.example.labweek7.ui.model.Wind


@Composable
fun Soal1WeatherInfoCard(
    main: Main,
    windSpeed: Wind,
    clouds: Clouds,
    rain: Rain
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Soal1InfoCard(title = "HUMIDITY",
                value = "${main.humidity}%",
                image = R.drawable.icon_humidity)

            Soal1InfoCard(title = "WIND",
                value = "${windSpeed.speed} km/h",
                image = R.drawable.icon_wind)

            Soal1InfoCard(title = "FEELS LIKE",
                value = "${main.feels_like}°",
                image = R.drawable.icon_feels_like)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Soal1InfoCard(title = "RAIN FALL",
                value = "${rain.oneHour} mm",
                image = R.drawable.vector_2)

            Soal1InfoCard(title = "PRESSURE",
                value = "${main.pressure} hPa",
                image = R.drawable.devices)

            Soal1InfoCard(title = "CLOUDS",
                value = "${clouds.all}%",
                image = R.drawable.cloud)
        }
    }
}



@Composable
@Preview()
fun Soal1WeatherInfoCardPreview(){
    val dummyMain = Main(temp = 25.0, feels_like = 26.0, humidity = 80, pressure = 1012)
    val dummyWind = Wind( speed = 10.5)
    val dummyClouds = Clouds(all = 50)
    val dummyRain = Rain(0.5)

    Soal1WeatherInfoCard(
        main = dummyMain,
        windSpeed = dummyWind,
        clouds = dummyClouds,
        rain = dummyRain
    )
}