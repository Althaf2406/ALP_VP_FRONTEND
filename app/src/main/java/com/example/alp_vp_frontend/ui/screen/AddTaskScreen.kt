package com.example.alp_vp_frontend.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alp_vp_frontend.viewmodel.ItemViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// --- COLOR PALETTE ---
private val BgGray = Color(0xFFF8F9FA)
private val CardWhite = Color(0xFFFFFFFF)
private val PrimaryPurple = Color(0xFF5F33E1)
private val TextGray = Color(0xFF8E8E93)
private val IconPinkBg = Color(0xFFFFD1DC)
private val IconPurpleBg = Color(0xFFEAE6FF)

@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: ItemViewModel
) {
    // Container Utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgGray)
            .statusBarsPadding() // PENTING: Agar header tidak tertutup jam/status bar HP
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 1. HEADER
        AddTaskHeader(onBack = { navController.popBackStack() })

        Spacer(modifier = Modifier.height(32.dp))

        // 2. FORM CONTENT
        AddTaskForm(
            viewModel = viewModel,
            onTaskCreated = { navController.popBackStack() }
        )
    }
}

@Composable
fun AddTaskHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            // Gunakan Icons.Filled.ArrowBack (Standar) agar pasti aman & bisa diklik
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Text(
            text = "Add Task",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        )

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notification",
            tint = Color.Black
        )
    }
}

@Composable
fun AddTaskForm(
    viewModel: ItemViewModel,
    onTaskCreated: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf("Hard") }
    val difficulties = listOf("Easy", "Medium", "Hard")

    // Picker Logic
    var selectedDateTime by remember {
        mutableStateOf(LocalDateTime.now().plusDays(1).withSecond(0).withNano(0))
    }
    val displayFormatter = DateTimeFormatter.ofPattern("d MMMM, yyyy HH:mm a")
    val backendFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val context = LocalContext.current

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            selectedDateTime = selectedDateTime.withHour(hour).withMinute(minute)
        },
        selectedDateTime.hour, selectedDateTime.minute, true
    )

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            selectedDateTime = selectedDateTime.withYear(year).withMonth(month + 1).withDayOfMonth(day)
            timePickerDialog.show()
        },
        selectedDateTime.year, selectedDateTime.monthValue - 1, selectedDateTime.dayOfMonth
    )

    // --- UI COMPONENTS ---

    // 1. FIXED GROUP FIELD (Task)
    InputCard(label = "Group") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconBox(color = IconPinkBg, icon = Icons.Filled.Work, tint = Color.White)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Task",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Color.Black),
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.Black)
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // 2. TASK NAME INPUT
    InputCard(label = "Task Name") {
        CustomTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = "Visual Programming Week 11 API"
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // 3. DESCRIPTION INPUT
    InputCard(label = "Description", minHeight = 120.dp) {
        CustomTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = "Making API for:\nCustomer\nOrder...",
            singleLine = false
        )
    }

    Spacer(modifier = Modifier.height(40.dp))

    // 4. DEADLINE PICKER
    InputCard(label = "Deadline") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconBox(color = IconPurpleBg, icon = Icons.Filled.DateRange, tint = PrimaryPurple)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = selectedDateTime.format(displayFormatter),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Color.Black),
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.Black)
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // 5. DIFFICULTY DROPDOWN
    var expanded by remember { mutableStateOf(false) }
    Box {
        InputCard(label = "Difficulty") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconBox(color = IconPinkBg, icon = Icons.Filled.TrackChanges, tint = Color.White)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = difficulty,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Color.Black),
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.Black)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            difficulties.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        difficulty = option
                        expanded = false
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(40.dp))

    // 6. ADD TASK BUTTON
    Button(
        onClick = {
            if (title.isNotBlank()) {
                val dateTimeString = selectedDateTime.format(backendFormatter)
                viewModel.createNewTask(title, description.ifEmpty { null }, dateTimeString, difficulty)
                onTaskCreated()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = "Add Task",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
    }
}

// --- HELPER COMPONENTS ---

@Composable
fun InputCard(
    label: String,
    minHeight: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .defaultMinSize(minHeight = minHeight)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(color = TextGray, fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black.copy(alpha = 0.5f), fontWeight = FontWeight.Bold)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = PrimaryPurple
        ),
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        singleLine = singleLine,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    )
}

@Composable
fun IconBox(color: Color, icon: ImageVector, tint: Color) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
    }
}