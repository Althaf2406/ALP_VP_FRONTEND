package com.example.alp_vp_frontend.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alp_vp_frontend.ui.components.DatePickerField
import com.example.alp_vp_frontend.ui.components.TimePickerField
import com.example.alp_vp_frontend.ui.viewmodel.ActivityViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ActivityUpdateView(
    navController: NavController,
    viewModel: ActivityViewModel,
    activityId: Int
) {
    val detailState by viewModel.detailActivityState.collectAsState()

    LaunchedEffect(activityId) {
        viewModel.getActivityById(activityId)
    }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var startTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime by remember { mutableStateOf(LocalTime.now()) }

    // State untuk menandai apakah data awal sudah dimuat ke form
    var isDataLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(detailState) {
        // Hanya isi form jika data ada DAN belum pernah diisi sebelumnya
        if (detailState != null && !isDataLoaded) {
            detailState?.data?.let { activity ->
                title = activity.title
                description = activity.description
                // Parsing string dari API ke LocalDate dan LocalTime
                startDate = LocalDate.parse(activity.start_date)
                endDate = LocalDate.parse(activity.end_date)
                startTime = LocalTime.parse(activity.start_time)
                endTime = LocalTime.parse(activity.end_time)
                isDataLoaded = true // Tandai bahwa data sudah dimuat
            }
        }
    }

    if (!isDataLoaded) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Update Activity", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })

            DatePickerField(label = "Start Date", selectedDate = startDate, onDateSelected = { startDate = it })
            DatePickerField(label = "End Date", selectedDate = endDate, onDateSelected = { endDate = it })

            TimePickerField(label = "Start Time", selectedTime = startTime, onTimeSelected = { startTime = it })
            TimePickerField(label = "End Time", selectedTime = endTime, onTimeSelected = { endTime = it })

            Button(onClick = {
                val updates = mutableMapOf<String, Any>()
                // Format ke string sebelum dikirim
                updates["title"] = title
                updates["description"] = description
                updates["start_date"] = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                updates["end_date"] = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                updates["start_time"] = startTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                updates["end_time"] = endTime.format(DateTimeFormatter.ofPattern("HH:mm"))

                viewModel.updateActivity(activityId, updates)
                navController.popBackStack()
            }) {
                Text("Save Changes")
            }
        }
    }
}
