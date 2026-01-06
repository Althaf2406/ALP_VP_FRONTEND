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
private val IconPurpleBg = Color(0xFFEAE6FF)
private val IconActivityBg = Color(0xFFD1C4E9)

@Composable
fun AddActivityScreen(
    navController: NavController,
    viewModel: ItemViewModel
) {
    // Container Utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgGray)
            .statusBarsPadding() // PENTING: Agar header turun ke area aman (tidak tertutup jam HP)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 1. HEADER
        AddActivityHeader(onBack = { navController.popBackStack() })

        Spacer(modifier = Modifier.height(32.dp))

        // 2. FORM CONTENT
        AddActivityForm(
            viewModel = viewModel,
            onActivityCreated = { navController.popBackStack() }
        )
    }
}

@Composable
fun AddActivityHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack, // Gunakan Filled standard agar aman diklik
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Text(
            text = "Add Activity",
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
fun AddActivityForm(
    viewModel: ItemViewModel,
    onActivityCreated: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // --- STATE WAKTU ---
    var startDateTime by remember { mutableStateOf(LocalDateTime.now().withSecond(0).withNano(0)) }
    var endDateTime by remember { mutableStateOf(LocalDateTime.now().plusHours(2).withSecond(0).withNano(0)) }

    var isSelectingStart by remember { mutableStateOf(true) }

    val displayFormatter = DateTimeFormatter.ofPattern("d MMMM, yyyy HH:mm a")
    val backendFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val context = LocalContext.current

    // --- PICKER LOGIC ---
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            val newTime = { dt: LocalDateTime -> dt.withHour(hour).withMinute(minute) }
            if (isSelectingStart) startDateTime = newTime(startDateTime) else endDateTime = newTime(endDateTime)
        },
        if (isSelectingStart) startDateTime.hour else endDateTime.hour,
        if (isSelectingStart) startDateTime.minute else endDateTime.minute,
        true
    )

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            val newDate = { dt: LocalDateTime -> dt.withYear(year).withMonth(month + 1).withDayOfMonth(day) }
            if (isSelectingStart) startDateTime = newDate(startDateTime) else endDateTime = newDate(endDateTime)
            timePickerDialog.show()
        },
        if (isSelectingStart) startDateTime.year else endDateTime.year,
        if (isSelectingStart) startDateTime.monthValue - 1 else endDateTime.monthValue - 1,
        if (isSelectingStart) startDateTime.dayOfMonth else endDateTime.dayOfMonth
    )

    // --- UI COMPONENTS ---

    // 1. FIXED GROUP FIELD (Activity)
    ActivityInputCard(label = "Group") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ActivityIconBox(color = IconActivityBg, icon = Icons.Filled.Person, tint = Color.White)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Activity",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Color.Black),
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.Black)
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // 2. ACTIVITY NAME INPUT
    ActivityInputCard(label = "Activity Name") {
        ActivityCustomTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = "Daily Standup Meeting"
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // 3. DESCRIPTION INPUT
    ActivityInputCard(label = "Description", minHeight = 120.dp) {
        ActivityCustomTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = "Project discussion...\nTeam updates...",
            singleLine = false
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // 4. START TIME PICKER
    ActivityInputCard(label = "Start Time") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isSelectingStart = true
                    datePickerDialog.show()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActivityIconBox(color = IconPurpleBg, icon = Icons.Filled.AccessTime, tint = PrimaryPurple)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = startDateTime.format(displayFormatter),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Color.Black),
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.Black)
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // 5. END TIME PICKER
    ActivityInputCard(label = "End Time") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isSelectingStart = false
                    datePickerDialog.show()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActivityIconBox(color = IconPurpleBg, icon = Icons.Filled.HistoryToggleOff, tint = PrimaryPurple)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = endDateTime.format(displayFormatter),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Color.Black),
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.Black)
        }
    }

    Spacer(modifier = Modifier.height(40.dp))

    // 6. ADD ACTIVITY BUTTON
    Button(
        onClick = {
            if (title.isNotBlank()) {
                val startStr = startDateTime.format(backendFormatter)
                val endStr = endDateTime.format(backendFormatter)
                viewModel.createNewActivity(title, description.ifEmpty { null }, startStr, endStr)
                onActivityCreated()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = "Add Activity",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
    }
}

// --- HELPER COMPONENTS (PREFIX "Activity") ---

@Composable
fun ActivityInputCard(
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
fun ActivityCustomTextField(
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
fun ActivityIconBox(color: Color, icon: ImageVector, tint: Color) {
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