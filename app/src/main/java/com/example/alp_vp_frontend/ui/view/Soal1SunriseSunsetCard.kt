package com.example.labweek8soal1.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labweek7.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@Composable
fun Soal1SunriseSunsetCard(sunriseTime: Long, sunsetTime: Long) {
    val sdf = SimpleDateFormat("hh:mm a")
    sdf.timeZone = TimeZone.getDefault()
    val sunrise = sdf.format(Date(sunriseTime * 1000))
    val sunset = sdf.format(Date(sunsetTime * 1000))

    Row{
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.5f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.vector),
                contentDescription = "Sunrise",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

                Text("Sunrise",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(sunrise,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.vector_21png),
                contentDescription = "Sunset",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Sunset",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(sunset,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
@Preview()
fun Soal1SunriseSunsetCardPreview() {
    Soal1SunriseSunsetCard(sunriseTime = 12371937, sunsetTime = 123177184)
}
