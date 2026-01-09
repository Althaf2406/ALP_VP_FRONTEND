//package com.example.alp_vp_frontend.ui.view
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import com.example.alp_vp_frontend.data.model.CreateActivityRequest
//import android.app.DatePickerDialog
//import java.time.Instant
//import java.util.Calendar
//import java.util.Date
//import java.text.SimpleDateFormat
//import java.util.Locale
//
//@Composable
//fun AddActivityContent(onSaveActivity: (CreateActivityRequest) -> Unit, modifier: Modifier = Modifier) {
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var selectedDateTimeMillis by remember { mutableStateOf<Long?>(null) }
//
//    val context = LocalContext.current
//    val calendar = Calendar.getInstance()
//
//    val datePickerDialog = remember {
//        DatePickerDialog(
//            context,
//            { _, year, month, dayOfMonth ->
//                calendar.set(year, month, dayOfMonth, 0, 0, 0)
//                selectedDateTimeMillis = calendar.timeInMillis
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
//        Text("Input Activity Details", style = MaterialTheme.typography.headlineSmall)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Activity Title") }, modifier = Modifier.fillMaxWidth())
//        Spacer(modifier = Modifier.height(16.dp))
//        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // --- FIELD DATE & TIME YANG BISA DIKLIK ---
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    datePickerDialog.show()
//                }
//        ) {
//            OutlinedTextField(
//                value = selectedDateTimeMillis?.let {
//                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it))
//                } ?: "Set Date & Time",
//                onValueChange = {},
//                readOnly = true,
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
//                    val deadline = selectedDateTimeMillis?.let { Instant.ofEpochMilli(it).toString() } ?: Instant.now().toString()
//
//                    onSaveActivity(CreateActivityRequest(title = title, description = description.ifBlank { null }, dateTime = deadline))
//                }
//            },
//            modifier = Modifier.fillMaxWidth().height(50.dp),
//            enabled = title.isNotBlank()
//        ) {
//            Text("Add Activity", style = MaterialTheme.typography.titleMedium)
//        }
//    }
//}