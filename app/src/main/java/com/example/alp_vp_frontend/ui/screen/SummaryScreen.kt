package com.example.alp_vp_frontend.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.alp_vp_frontend.R
import com.example.alp_vp_frontend.data.model.Item
import com.example.alp_vp_frontend.navigation.Screen
import com.example.alp_vp_frontend.viewmodel.ItemViewModel
import com.example.alp_vp_frontend.viewmodel.UiState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// --- DEFINISI WARNA SESUAI DESAIN ---
val PurplePrimary = Color(0xFF5F33E1)
val PurpleLightButton = Color(0xFFEAE6FF)
val BackgroundWhite = Color(0xFFFFFFFF)
val TextBlack = Color(0xFF1A1A1A)
val TextGrey = Color(0xFF8E8E93)
val CardBlueLight = Color(0xFFE6F5FC)
val CardPeachLight = Color(0xFFFFEFE6)
val IconPink = Color(0xFFFFD1DC)
val IconPurple = Color(0xFFD1C4E9)
val BottomNavBg = Color(0xFF212121)
val BottomNavOrange = Color(0xFFFF8F00)

@Composable
fun SummaryScreen(
    navController: NavController,
    viewModel: ItemViewModel
) {
    // 1. DATA LOADING LOGIC
    LaunchedEffect(key1 = true) {
        viewModel.loadData()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadData()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val uiState by viewModel.uiState.collectAsState()

    val inProgressItems = uiState.allItems.filter { !it.isCompleted }.take(2)

    // 2. STRUKTUR UI UTAMA
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Area Scrollable
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // A. Header
            HeaderSection()
            Spacer(modifier = Modifier.height(24.dp))

            // B. Banner Ungu
            BannerSection(uiState = uiState)
            Spacer(modifier = Modifier.height(24.dp))

            // C. In Progress Section
            if (inProgressItems.isNotEmpty()) {
                InProgressSection(items = inProgressItems)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // D. Task List (List Vertikal)
            Text(
                text = "Your Tasks (${uiState.allItems.size})",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextBlack
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (uiState.allItems.isEmpty()) {
                Text("No tasks yet. Add one below!", color = TextGrey)
            } else {
                // Render List Item
                uiState.allItems.forEach { item ->
                    TaskItemCard(
                        item = item,
                        onDelete = { viewModel.deleteItem(item) },
                        onToggle = { viewModel.toggleItemCompletion(item) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // E. Tombol Aksi
            ActionButtonsSection(navController)
            Spacer(modifier = Modifier.height(24.dp))
        }

        // F. Bottom Navigation
        CustomBottomNavigationBar()
    }
}

// --- KOMPONEN UI ---

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Placeholder Profile
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Hello!", fontSize = 16.sp, color = TextGrey)
                Text(text = "Mualli Hilmi", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextBlack)
            }
        }
        Box {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                modifier = Modifier.size(28.dp),
                tint = TextBlack
            )
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color.Red, CircleShape)
                    .align(Alignment.TopEnd)
                    .offset(x = 2.dp, y = (-2).dp)
            )
        }
    }
}

@Composable
fun BannerSection(uiState: UiState) {
    val percentageInt = uiState.summary.todayAlmostDonePercentage.coerceIn(0, 100)
    val percentageFloat = percentageInt / 100f

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PurplePrimary)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Your today's task almost done!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    lineHeight = 26.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleLightButton),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "View Task",
                        color = PurplePrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Percentage", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                Spacer(modifier = Modifier.height(12.dp))

                CircularProgressBar(
                    percentage = percentageFloat,
                    size = 100.dp,
                    strokeWidth = 10.dp,
                    fontSize = 20.sp,
                    textColor = Color.White,
                    progressColor = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
            }
        }
    }
}

@Composable
fun InProgressSection(items: List<Item>) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "In Progress", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextBlack)
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "See All", tint = TextBlack)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            items.forEachIndexed { index, item ->
                val isFirst = index % 2 == 0
                val bgColor = if (isFirst) CardBlueLight else CardPeachLight
                val iconBg = if (isFirst) IconPink else IconPurple
                val icon = if (isFirst) Icons.Filled.Work else Icons.Filled.Person
                val label = if (isFirst) "Task" else "Activity"

                ProgressItemCard(
                    modifier = Modifier.weight(1f),
                    backgroundColor = bgColor,
                    iconBgColor = iconBg,
                    icon = icon,
                    label = label,
                    title = item.title
                )

                if (index == 0 && items.size > 1) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }

            if (items.size == 1) {
                Spacer(modifier = Modifier.width(16.dp))
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

// --- ITEM CARD (YANG DIUBAH MENAMPILKAN TANGGAL/WAKTU) ---
@Composable
fun TaskItemCard(
    item: Item,
    onDelete: () -> Unit,
    onToggle: () -> Unit
) {
    // 1. Tentukan Icon berdasarkan tipe item
    val isTask = item.type == "Task"
    val iconBgColor = if (isTask) IconPink else IconPurple
    val icon = if (isTask) Icons.Filled.Work else Icons.Filled.Person

    // 2. Logic Formatter Tanggal/Waktu
    val formattedDateText = remember(item) {
        try {
            // Ambil string tanggal (Task = dateTime, Activity = startDateTime)
            val rawDate = if (isTask) item.dateTime else item.startDateTime

            if (!rawDate.isNullOrEmpty()) {
                // Hapus 'Z' jika ada, agar bisa diparsing sebagai Local Time
                val cleanDate = rawDate.replace("Z", "")
                val parsed = LocalDateTime.parse(cleanDate)
                // Format: 10 Jan 2025, 14:00
                parsed.format(DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm"))
            } else {
                "No Deadline"
            }
        } catch (e: Exception) {
            // Fallback jika parsing gagal
            if (isTask) item.dateTime ?: "" else item.startDateTime ?: ""
        }
    }

    // 3. Status Progress (Lingkaran)
    val itemProgress = if (item.isCompleted) 1.0f else 0.0f

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        elevation = CardDefaults.cardElevation(0.dp),
        onClick = onToggle
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Box
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(iconBgColor, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Title & Date/Time (GANTI STATUS TEXT DISINI)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBlack,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Menampilkan Tanggal/Waktu Deadline
                Text(
                    text = formattedDateText,
                    fontSize = 14.sp,
                    color = TextGrey,
                    fontWeight = FontWeight.Medium
                )
            }

            // Delete Button
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.5f))
            }

            // Progress Circle
            CircularProgressBar(
                percentage = itemProgress,
                size = 50.dp,
                strokeWidth = 5.dp,
                fontSize = 12.sp,
                textColor = TextBlack,
                progressColor = PurplePrimary,
                trackColor = PurplePrimary.copy(alpha = 0.2f)
            )
        }
    }
}

@Composable
fun ActionButtonsSection(navController: NavController) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = { navController.navigate(Screen.AddTask.route) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Add Task", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Button(
            onClick = { navController.navigate(Screen.AddActivity.route) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Add Activity", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun CustomBottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BottomNavBg)
            .padding(vertical = 16.dp, horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Home, "Home", tint = Color.White, modifier = Modifier.size(28.dp))
        Icon(Icons.Filled.AccountBalanceWallet, "Wallet", tint = Color.White, modifier = Modifier.size(28.dp))
        Icon(Icons.Filled.AccessTime, "History", tint = Color.White, modifier = Modifier.size(28.dp))

        Row(
            modifier = Modifier
                .background(BottomNavOrange.copy(alpha = 0.2f), RoundedCornerShape(50))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.List, "Task", tint = BottomNavOrange, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Task", color = BottomNavOrange, fontWeight = FontWeight.Bold)
        }

        Icon(Icons.Outlined.Person, "Profile", tint = Color.White, modifier = Modifier.size(28.dp))
    }
}

// --- HELPER VISUAL ---
@Composable
fun ProgressItemCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    iconBgColor: Color,
    icon: ImageVector,
    label: String,
    title: String
) {
    Card(
        modifier = modifier.height(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = label, fontSize = 14.sp, color = TextGrey)
                Box(
                    modifier = Modifier.size(32.dp).background(iconBgColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextBlack, maxLines = 3)
        }
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    size: Dp,
    strokeWidth: Dp,
    fontSize: androidx.compose.ui.unit.TextUnit,
    textColor: Color,
    progressColor: Color,
    trackColor: Color
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(color = trackColor, startAngle = 0f, sweepAngle = 360f, useCenter = false, style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round))
            drawArc(color = progressColor, startAngle = -90f, sweepAngle = 360 * percentage, useCenter = false, style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round))
        }
        Text(text = "${(percentage * 100).toInt()}%", fontSize = fontSize, fontWeight = FontWeight.Bold, color = textColor)
    }
}