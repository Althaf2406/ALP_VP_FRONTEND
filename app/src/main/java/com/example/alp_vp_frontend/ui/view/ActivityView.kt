package com.example.alp_vp_frontend.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alp_vp_frontend.ui.viewmodel.ActivityViewModel
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ActivityView(
    navController : NavController,
    authViewModel: AuthViewModel,
    activityViewModel: ActivityViewModel
) {
    // State untuk bulan yang sedang ditampilkan
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    // State untuk tanggal yang sedang dipilih
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    // Mengambil data aktivitas dari ViewModel
    val activities by activityViewModel.activitiesByDate.collectAsState()

    // Memuat data saat pertama kali layar dibuka
//    LaunchedEffect(Unit) {
//        viewModel.getAllActivities()
//    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // 1. Header Kalender (Nama Bulan, Tahun, dan Tombol Navigasi)
            CalendarHeader(
                currentMonth = currentMonth,
                onPreviousMonth = { currentMonth = currentMonth.minusMonths(1) },
                onNextMonth = { currentMonth = currentMonth.plusMonths(1) }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 2. Header Hari (Sen, Sel, Rab, ...)
            DaysOfWeekHeader()
            Spacer(modifier = Modifier.height(8.dp))

            // 3. Grid Tanggal
            CalendarGrid(
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                activities = activities,
                onDateClick = { date ->
                    selectedDate = date

                    val route = "daily_schedule_screen/${date}"
                    navController.navigate(route)
                    // navController.navigate("detail_tanggal/${date}")
                }
            )
        }
    }



// Composable untuk Header Kalender
@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Previous Month")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = currentMonth.year.toString(),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        IconButton(onClick = onNextMonth) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next Month")
        }
    }
}


// Composable untuk Header Hari
@Composable
fun DaysOfWeekHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        days.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}


// Composable untuk Grid Tanggal
@Composable
fun CalendarGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    activities: Map<LocalDate, List<Any>>,
    onDateClick: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value // 1 (Senin) - 7 (Minggu)
    val daysInMonth = currentMonth.lengthOfMonth()

    // Hitung offset untuk hari pertama
    val dayOffset = (firstDayOfWeek - 1) % 7

    Column {
        // Buat 6 baris untuk mengakomodasi semua kemungkinan tata letak bulan
        for (week in 0 until 6) {
            Row(Modifier.fillMaxWidth()) {
                for (day in 1..7) {
                    val dayOfMonth = (week * 7) + day - dayOffset
                    if (dayOfMonth > 0 && dayOfMonth <= daysInMonth) {
                        val date = currentMonth.atDay(dayOfMonth)
                        DayCell(
                            date = date,
                            isSelected = date == selectedDate,
                            hasActivity = activities.containsKey(date),
                            onClick = {
                                // Panggil fungsi onDateClick dengan tanggal yang dipilih
                                onDateClick(date)
                            }
                        )
                    } else {
                        // Sel kosong untuk mengisi sisa grid
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}


// Composable untuk setiap sel tanggal
@Composable
fun RowScope.DayCell(
    date: LocalDate,
    isSelected: Boolean,
    hasActivity: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f) // Membuat sel menjadi persegi
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
            )
            if (hasActivity) {
                Spacer(modifier = Modifier.height(4.dp))
                // Indikator aktivitas (titik-titik)
                Row {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) Color.White.copy(alpha = 0.7f) else Color.Blue.copy(alpha = 0.5f))
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
    }
}

