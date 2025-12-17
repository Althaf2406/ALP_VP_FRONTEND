package com.example.alp_vp_frontend.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.alp_vp_frontend.data.model.Item
import com.example.alp_vp_frontend.navigation.Screen
import com.example.alp_vp_frontend.ui.view.ItemCard
import com.example.alp_vp_frontend.viewmodel.ItemViewModel
import com.example.alp_vp_frontend.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    navController: NavController,
    viewModel: ItemViewModel
) {
    // Memuat data saat composable pertama kali masuk (Initial Load)
    LaunchedEffect(key1 = true) {
        viewModel.loadData()
    }

    // Solusi untuk memuat data saat kembali dari AddTask/AddActivity
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadData()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // PENTING: Pindahkan deklarasi uiState dan showMenu KELUAR dari LaunchedEffect
    val uiState by viewModel.uiState.collectAsState()
    val showMenu = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productivity Dashboard", fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton(onClick = viewModel::loadData) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh Data")
                    }
                }
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                // Dropdown untuk Tambah Task/Activity
                DropdownMenu(
                    expanded = showMenu.value,
                    onDismissRequest = { showMenu.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("➕ Tambah Task") },
                        onClick = {
                            showMenu.value = false
                            navController.navigate(Screen.AddTask.route)
                        }
                    )
                    Divider()
                    DropdownMenuItem(
                        text = { Text("🗓️ Tambah Activity") },
                        onClick = {
                            showMenu.value = false
                            navController.navigate(Screen.AddActivity.route)
                        }
                    )
                }

                FloatingActionButton(onClick = { showMenu.value = true }) {
                    Icon(Icons.Filled.Add, contentDescription = "Tambah Item Baru")
                }
            }
        }
    ) { padding ->
        SummaryContent(
            uiState = uiState,
            onToggleComplete = viewModel::toggleItemCompletion,
            onDelete = viewModel::deleteItem, // <-- INTEGRASI FUNGSI DELETE
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun SummaryContent(
    uiState: UiState,
    onToggleComplete: (item: Item) -> Unit,
    onDelete: (item: Item) -> Unit, // <-- TERIMA FUNGSI DELETE
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {

        // 1. Summary Header (Progress)
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Progress Harian", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                Spacer(Modifier.height(8.dp))

                // Mengambil nilai progress dan memastikan berada di antara 0-100
                val percentage = uiState.summary.todayAlmostDonePercentage.coerceIn(0, 100)
                val progressFloat = percentage / 100f // Konversi ke Float (0.0 - 1.0)

                Text(
                    text = "$percentage%",
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                LinearProgressIndicator(
                    progress = progressFloat,
                    modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 2. Error dan Loading
        if (uiState.isLoading && uiState.allItems.isEmpty()) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        uiState.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(vertical = 8.dp))
        }

        // 3. Daftar Semua Item
        Text("Semua Jadwal (${uiState.allItems.size})",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp))

        if (uiState.allItems.isEmpty() && !uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tidak ada Task atau Activity.\nMari buat yang pertama!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
                items(uiState.allItems, key = { it.id }) { item ->
                    ItemCard(
                        item = item,
                        onToggleComplete = onToggleComplete,
                        onDelete = onDelete // <-- TERUSKAN FUNGSI DELETE KE ItemCard
                    )
                }
            }
        }
    }
}