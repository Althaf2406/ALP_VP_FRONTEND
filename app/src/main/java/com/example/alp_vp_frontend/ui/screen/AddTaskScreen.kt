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
fun AddTaskScreen(
    navController: NavController,
    viewModel: ItemViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Task Baru", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        AddTaskContent(
            viewModel = viewModel,
            onTaskCreated = { navController.popBackStack() },
            modifier = Modifier.padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskContent(
    viewModel: ItemViewModel,
    onTaskCreated: () -> Unit,
    modifier: Modifier = Modifier
) {
    // --- STATE INPUT ---
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // STATE DIFFICULTY
    var difficulty by remember { mutableStateOf("None") } // <-- STATE INI SUDAH KEMBALI
    val difficulties = listOf("None", "Easy", "Medium", "Hard") // <-- DAFTAR INI SUDAH KEMBALI

    // STATE DEADLINE (Picker Logic)
    var selectedDateTime by remember {
        mutableStateOf(LocalDateTime.now().plusDays(1).withSecond(0).withNano(0))
    }

    val displayFormatter = remember {
        DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
    }
    val backendFormatter = remember {
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }

    val context = LocalContext.current

    // --- Time Picker Dialog Setup ---
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            selectedDateTime = selectedDateTime
                .withHour(hour)
                .withMinute(minute)
        },
        selectedDateTime.hour,
        selectedDateTime.minute,
        true // Format 24 jam
    )

    // --- Date Picker Dialog Setup ---
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            selectedDateTime = selectedDateTime
                .withYear(year)
                .withMonth(month + 1)
                .withDayOfMonth(day)
            timePickerDialog.show() // Tampilkan Time Picker setelah Date dipilih
        },
        selectedDateTime.year,
        selectedDateTime.monthValue - 1,
        selectedDateTime.dayOfMonth
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Detail Task", style = MaterialTheme.typography.headlineSmall)

        // --- Input Judul & Deskripsi ---
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul Task") },
            placeholder = { Text("Contoh: Finalisasi Laporan ALP") },
            isError = title.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Deskripsi (Opsional)") },
            modifier = Modifier.fillMaxWidth()
        )

        // --- GANTI TEXT FIELD DENGAN BUTTON UNTUK MEMICU PICKER ---
        OutlinedButton(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
        ) {
            Text(
                "Deadline: ${selectedDateTime.format(displayFormatter)}",
                fontWeight = FontWeight.Normal
            )
        }

        // --- INPUT DIFFICULTY (Dropdown) ---
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = true,
                value = difficulty,
                onValueChange = {},
                label = { Text("Difficulty") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                difficulties.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            difficulty = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    // Konversi objek LocalDateTime ke string ISO 8601
                    val dateTimeString = selectedDateTime.format(backendFormatter)

                    // ViewModel akan menambahkan 'Z' di akhirnya
                    viewModel.createNewTask(
                        title,
                        description.ifEmpty { null },
                        dateTimeString,
                        difficulty
                    )
                    onTaskCreated()
                }
            },
            enabled = title.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Simpan Task")
        }
    }
}