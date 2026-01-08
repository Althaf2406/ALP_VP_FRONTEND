package com.example.alp_vp_frontend.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alp_vp_frontend.data.dto.Data
import com.example.alp_vp_frontend.ui.viewmodel.ActivityViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// Warna-warni untuk kartu aktivitas
private val cardColors = listOf(
    Color(0xFFE0F7FA), // Light Cyan
    Color(0xFFFFEBEE), // Light Pink
    Color(0xFFFFF3E0), // Light Orange
    Color(0xFFE8EAF6), // Light Indigo
    Color(0xFFF1F8E9)  // Light Green
)

@Composable
fun DailyScheduleView(
    calenderViewModel: ActivityViewModel,
    date: String // Menerima tanggal sebagai String dari argumen navigasi
) {
    // Ubah String tanggal menjadi objek LocalDate
    val selectedDate = LocalDate.parse(date)
    val activitiesByDate by calenderViewModel.activitiesByDate.collectAsState()

    // Ambil daftar aktivitas untuk tanggal yang dipilih, urutkan berdasarkan waktu mulai
    val activitiesForDay = activitiesByDate[selectedDate]?.sortedBy { it.start_time } ?: emptyList()

        if (activitiesForDay.isEmpty()) {
            // JIKA TIDAK ADA AKTIVITAS, TAMPILKAN PESAN INI
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No activity yet",
                    fontSize = 18.sp,
                    color = Color.Gray,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                items(activitiesForDay.size) { index ->
                    val activity = activitiesForDay[index]
                    // Setiap item di list adalah sebuah TimelineNode
                    TimelineNode(
                        activity = activity,
                        cardColor = cardColors[index % cardColors.size] // Ambil warna secara bergiliran
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }


@Composable
fun TimelineNode(
    activity: Data,
    cardColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Kolom untuk Waktu dan Garis
        TimeAndLine(time = activity.start_time)

        // Kolom untuk Kartu Aktivitas
        Column(modifier = Modifier.weight(1f)) {
            ActivityCard(activity = activity, color = cardColor)
        }
    }
}

@Composable
fun TimeAndLine(time: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tampilkan waktu mulai
        Text(text = time, fontSize = 14.sp, color = Color.Gray)

        // Garis vertikal di bawah waktu
        val lineColor = Color.LightGray
        Canvas(modifier = Modifier
            .height(100.dp)
            .width(20.dp)) {
            drawLine(
                color = lineColor,
                start = Offset(x = center.x, y = 0f),
                end = Offset(x = center.x, y = size.height),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

@Composable
fun ActivityCard(activity: Data, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = activity.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            if (activity.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = activity.description,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}
