package com.example.labweek7.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.labweek7.R
import com.example.labweek7.ui.model.WeatherDisplay
import com.example.labweek7.ui.viewmodel.Soal1WeatherViewModel

@Composable
fun Soal1(
    s1VM: Soal1WeatherViewModel
) {
    val state = s1VM.uState
    var query by remember { mutableStateOf(TextFieldValue("")) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.weather___home_2),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon")
                    },
                    placeholder = { Text("Enter city name...") },
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .weight(0.7f)
                        .height(52.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White.copy(alpha = 0.2f),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                        focusedTextColor = Color.White,
                    ),
                    singleLine = true
                )

                Button(
                    onClick = {
                        s1VM.updateSearchQuery(query.text)
                        s1VM.fetchWeather()
                    },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(52.dp)
                        .width(120.dp)
                ) {
                    Icon(Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Search", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    when {
                        state.isLoading -> {
                            CircularProgressIndicator(color = Color.White)
                        }

                        state.eMsg != null -> {
                            Icon(
                                imageVector = Icons.Filled.Warning, 
                                contentDescription = "Error Icon",
                                tint = Color.Red,
                                modifier = Modifier.size(80.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))


                            Text(
                                text = "Oops! Something went wrong",
                                color = Color.White,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "HTTP 404 Not Found",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )

                        }

                        state.wthrData != null -> {
                            Soal1WeatherResultCard(
                                dataWDisplay = state.wthrData,
                                dataWeather = state.wthrData.weather?.firstOrNull()!!
                            )
                        }

                        else -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillParentMaxSize()
                            ) {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search",
                                    modifier = Modifier.size(75.dp),
                                    tint = Color(0xFF9FA3B7)
                                )
                                Text(
                                    "Search a city to get started",
                                    fontSize = 20.sp,
                                    color = Color(0xFF9FA3B7)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal1Preview() {
    Soal1(
        s1VM = viewModel()
    )
}
