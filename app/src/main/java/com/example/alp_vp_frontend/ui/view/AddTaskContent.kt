//package com.example.alp_vp_frontend.ui.view
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import com.example.alp_vp_frontend.data.model.CreateTaskRequest
//import android.app.DatePickerDialog
//import java.time.Instant
//import java.util.Calendar
//import java.util.Date
//import java.text.SimpleDateFormat
//import java.util.Locale
//
//@Composable
//fun AddTaskContent(onSaveTask: (CreateTaskRequest) -> Unit, modifier: Modifier = Modifier) {
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var difficulty by remember { mutableStateOf("Easy") }
//    val difficultyOptions = listOf("Easy", "Medium", "Hard")
//
//    // State untuk menyimpan waktu yang dipilih (dalam milidetik)
//    var selectedDateTimeMillis by remember { mutableStateOf<Long?>(null) }
//
//    val context = LocalContext.current
//    val calendar = Calendar.getInstance()
//
//    // Fungsi untuk menampilkan Date Picker
//    val datePickerDialog = remember {
//        DatePickerDialog(
//            context,
//            // Aksi saat tanggal dipilih
//            { _, year, month, dayOfMonth ->
//                // Set tanggal yang dipilih, dan waktu ke tengah malam sebagai default
//                calendar.set(year, month, dayOfMonth, 0, 0, 0)
//                selectedDateTimeMillis = calendar.timeInMillis
//                // Anda bisa menambahkan TimePickerDialog di sini jika dibutuhkan
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        )
//    }
//
//    Column(
//        modifier = modifier.fillMaxSize().padding(16.dp)
//    ) {
//        Text("Input Task Details", style = MaterialTheme.typography.headlineSmall)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Task Title") }, modifier = Modifier.fillMaxWidth())
//        Spacer(modifier = Modifier.height(16.dp))
//        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
//
//        Spacer(modifier = Modifier.height(24.dp))
//        Text("Difficulty:", style = MaterialTheme.typography.titleMedium)
//
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            difficultyOptions.forEach { diff ->
//                ChoiceChip(label = diff, selected = difficulty == diff, onClick = { difficulty = diff })
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // --- FIELD DATE & TIME YANG BISA DIKLIK ---
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    datePickerDialog.show() // Menampilkan Date Picker
//                }
//        ) {
//            OutlinedTextField(
//                value = selectedDateTimeMillis?.let {
//                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it))
//                } ?: "Set Date & Time",
//                onValueChange = {},
//                readOnly = true, // Tetap readOnly, tapi sekarang ada aksi klik
//                label = { Text("Due Date") },
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        Button(
//            onClick = {
//                if (title.isNotBlank()) {
//                    // Gunakan selectedDateTimeMillis atau waktu sekarang jika null
//                    val deadline = selectedDateTimeMillis?.let { Instant.ofEpochMilli(it).toString() } ?: Instant.now().toString()
//
//                    onSaveTask(CreateTaskRequest(title = title, description = description.ifBlank { null }, dateTime = deadline, difficulty = difficulty))
//                }
//            },
//            modifier = Modifier.fillMaxWidth().height(50.dp),
//            enabled = title.isNotBlank()
//        ) {
//            Text("Add Task", style = MaterialTheme.typography.titleMedium)
//        }
//    }
//}