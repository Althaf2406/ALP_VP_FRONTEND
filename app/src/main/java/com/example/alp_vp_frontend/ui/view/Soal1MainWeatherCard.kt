package com.example.labweek7.ui.view

import android.R.attr.icon
import android.R.attr.label
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.labweek7.R
import com.example.labweek7.data.container.WeatherContainer
import com.example.labweek7.ui.model.Weather
import com.example.labweek7.ui.model.WeatherDisplay

@Composable
fun Soal1MainWeatherCard(
    condition: String,
    temperature: Double,
    dataWDisplay: WeatherDisplay,
    dataWeather: Weather
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${dataWeather.icon}@4x.png",
                    contentDescription = "null",
                    modifier = Modifier.size(100.dp)
                )

                Text(condition,
                    fontSize = 22.sp,
                    color = Color.White)

                Text("${temperature.toInt()}°C",
                    fontSize = 42.sp, color = Color.White,
                    fontWeight = FontWeight.Bold)
            }
            Image(
                painter = painterResource(
                    when (condition.lowercase()) {
                        "rain" -> R.drawable.blue_and_black_bold_typography_quote_poster_2
                        "clouds" -> R.drawable.blue_and_black_bold_typography_quote_poster
                        "clear" -> R.drawable.blue_and_black_bold_typography_quote_poster_3
                        else -> R.drawable.blue_and_black_bold_typography_quote_poster
                    }
                ),
                contentDescription = condition,
                modifier = Modifier.size(120.dp)
            )
        }


    }
}

@Composable
@Preview()
fun Soal1MainWeatherCardPreview() {
    val dummyWeatherDisplay = WeatherDisplay(
        cod = 200,
        name = "Cepu",
        sys = null,
        clouds = null,
        wind = null,
        main = null,
        weather = null,
        rain = null
    )

    Soal1MainWeatherCard(
        condition = "Clouds",
        temperature = 25.0,
        dataWDisplay = dummyWeatherDisplay,
        dataWeather = Weather(
            main = "Clouds",
            icon = "04d",
            description = "broken clouds"
        )
    )
}
