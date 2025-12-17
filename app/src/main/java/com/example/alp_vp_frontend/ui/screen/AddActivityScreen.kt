package com.example.alp_vp_frontend.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
// --- Imports Picker ---
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.ui.platform.LocalContext
import java.time.format.DateTimeFormatter
// --- Akhir Imports Picker ---
import androidx.navigation.NavController
import com.example.alp_vp_frontend.viewmodel.ItemViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityScreen(
    navController: NavController,
    viewModel: ItemViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Activity Baru", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        AddActivityContent(
            viewModel = viewModel,
            onActivityCreated = { navController.popBackStack() },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun AddActivityContent(
    viewModel: ItemViewModel,
    onActivityCreated: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // --- STATE DEADLINE BARU (LocalDateTime) ---
    var startDateTime by remember { mutableStateOf(LocalDateTime.now().withSecond(0).withNano(0)) }
    var endDateTime by remember { mutableStateOf(LocalDateTime.now().plusHours(2).withSecond(0).withNano(0)) }

    // STATE untuk melacak apakah kita sedang memilih waktu Mulai (true) atau Selesai (false)
    var isSelectingStart by remember { mutableStateOf(true) }

    // Format yang ditampilkan ke pengguna (mudah dibaca)
    val displayFormatter = remember {
        DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
    }

    // Format yang dikirim ke backend (ISO 8601)
    val backendFormatter = remember {
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }

    val context = LocalContext.current


    // --- Time Picker Dialog Setup ---
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            val newTime = { dt: LocalDateTime -> dt.withHour(hour).withMinute(minute) }

            if (isSelectingStart) {
                startDateTime = newTime(startDateTime)
            } else {
                endDateTime = newTime(endDateTime)
            }
        },
        if (isSelectingStart) startDateTime.hour else endDateTime.hour,
        if (isSelectingStart) startDateTime.minute else endDateTime.minute,
        true // Format 24 jam
    )

    // --- Date Picker Dialog Setup ---
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            val newDate = { dt: LocalDateTime ->
                dt.withYear(year).withMonth(month + 1).withDayOfMonth(day)
            }

            if (isSelectingStart) {
                startDateTime = newDate(startDateTime)
            } else {
                endDateTime = newDate(endDateTime)
            }
            timePickerDialog.show() // Tampilkan Time Picker segera setelah Date dipilih
        },
        if (isSelectingStart) startDateTime.year else endDateTime.year,
        if (isSelectingStart) startDateTime.monthValue - 1 else endDateTime.monthValue - 1,
        if (isSelectingStart) startDateTime.dayOfMonth else endDateTime.dayOfMonth
    )


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Detail Activity", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul Activity") },
            placeholder = { Text("Contoh: Rapat Harian Tim Proyek") },
            isError = title.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Deskripsi (Opsional)") },
            modifier = Modifier.fillMaxWidth()
        )

        // --- INPUT WAKTU MULAI (BUTTON PICKER) ---
        OutlinedButton(
            onClick = {
                isSelectingStart = true
                datePickerDialog.show()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
        ) {
            Text(
                "Waktu Mulai: ${startDateTime.format(displayFormatter)}",
                fontWeight = FontWeight.Normal
            )
        }

        // --- INPUT WAKTU SELESAI (BUTTON PICKER) ---
        OutlinedButton(
            onClick = {
                isSelectingStart = false
                datePickerDialog.show()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
        ) {
            Text(
                "Waktu Selesai: ${endDateTime.format(displayFormatter)}",
                fontWeight = FontWeight.Normal
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    // Konversi objek LocalDateTime ke string ISO 8601 (Contoh: 2025-12-31T23:59:00)
                    val startDateTimeString = startDateTime.format(backendFormatter)
                    val endDateTimeString = endDateTime.format(backendFormatter)

                    // ViewModel akan menambahkan 'Z' di akhirnya
                    viewModel.createNewActivity(
                        title,
                        description.ifEmpty { null },
                        startDateTimeString,
                        endDateTimeString
                    )
                    onActivityCreated()
                }
            },
            enabled = title.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Simpan Activity")
        }
    }
}