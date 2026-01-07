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
import com.example.alp_vp_frontend.data.dto.NewActivity
import com.example.alp_vp_frontend.ui.components.DatePickerField
import com.example.alp_vp_frontend.ui.components.TimePickerField
import com.example.alp_vp_frontend.ui.viewmodel.ActivityViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ActivityCreateView(
    navController: NavController,
    viewModel: ActivityViewModel
) {
    // Gunakan state LocalDate dan LocalTime
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var startTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime by remember { mutableStateOf(LocalTime.now().plusHours(1)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Create New Activity", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })

        // Gunakan komponen DatePickerField
        DatePickerField(label = "Start Date", selectedDate = startDate, onDateSelected = { startDate = it })
        DatePickerField(label = "End Date", selectedDate = endDate, onDateSelected = { endDate = it })

        // Gunakan komponen TimePickerField
        TimePickerField(label = "Start Time", selectedTime = startTime, onTimeSelected = { startTime = it })
        TimePickerField(label = "End Time", selectedTime = endTime, onTimeSelected = { endTime = it })

        Button(onClick = {
            val newActivity = NewActivity(
                title = title,
                description = description,
                start_date = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE), // Format ke string
                end_date = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                start_time = startTime.format(DateTimeFormatter.ofPattern("HH:mm")), // Format ke string
                end_time = endTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            )
            viewModel.createActivity(newActivity)
            navController.popBackStack()
        }) {
            Text("Save Activity")
        }
    }
}
