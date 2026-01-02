//package com.example.labweek7.ui.view
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Place
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.labweek7.R
//import java.text.SimpleDateFormat
//import java.util.*
//
//@Composable
//fun Soal1WeatherHeaderCard(
//    city: String,
//    date: String,
//    updatedTime: String
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Icon(
//                imageVector = Icons.Default.Place,
//                contentDescription = "Location",
//                tint = Color.White,
//                modifier = Modifier.size(18.dp)
//            )
//
//            Spacer(Modifier.width(4.dp))
//
//            Text(city, color = Color.White,
//                fontSize = 18.sp)
//        }
//
//        Text(date, color = Color.White,
//            fontSize = 28.sp,
//            fontWeight = FontWeight.Bold)
//
//        Text("Updated as of $updatedTime",
//            color = Color.White.copy(alpha = 0.6f),
//            fontSize = 14.sp)
//    }
//}
//
//@Composable
//@Preview()
//fun Soal1WeatherHeaderPreview() {
//    Soal1WeatherHeaderCard(
//        city = "Cepu",
//        date = SimpleDateFormat("MMMM dd", Locale.getDefault()).format(Date()),
//        updatedTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
//    )
//}
