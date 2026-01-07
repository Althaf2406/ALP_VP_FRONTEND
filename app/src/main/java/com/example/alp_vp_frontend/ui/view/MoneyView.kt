package com.example.alp_vp_frontend.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alp_vp_frontend.MoneyViewMode
import com.example.alp_vp_frontend.data.repository.AuthRepository
import com.example.alp_vp_frontend.ui.model.MoneyModel
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.temporal.WeekFields
import java.util.Locale


@Composable
fun MoneyView(
    moneyViewModel: MoneyViewModel,
    authViewModel: AuthViewModel,
    viewMode: MoneyViewMode,
    onChangeViewMode: (MoneyViewMode) -> Unit
) {
    val moneyList by moneyViewModel.moneyList.collectAsState()
    val loading by moneyViewModel.loading.collectAsState()
    val error by moneyViewModel.error.collectAsState()

    // ⬇️ derived state dari ViewModel
    val totalIncome by moneyViewModel.totalIncome.collectAsState()
    val totalOutcome by moneyViewModel.totalOutcome.collectAsState()

    var viewMode by remember {
        mutableStateOf(MoneyViewMode.LIST)
    }
    var showAdd by remember { mutableStateOf(false) }


    // ==========================
    // ADD TRANSACTION SCREEN
    // ==========================

    if (showAdd) {
        AddTransactionView(
            moneyViewModel = moneyViewModel,
            onSuccess = { showAdd = false },
            authViewModel = authViewModel
        )
    } else {
        // seluruh UI list di sini


        // ==========================
        // MAIN MONEY VIEW
        // ==========================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F6F6))
        ) {

            // -------- ADD BUTTON --------
            Button(
                onClick = { showAdd = true },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Tambah Transaksi")
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewMode = MoneyViewMode.LIST },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (viewMode == MoneyViewMode.LIST)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Transaksi")
                }

                Button(
                    onClick = { viewMode = MoneyViewMode.GRAPH },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (viewMode == MoneyViewMode.GRAPH)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Grafik")
                }
            }



            when {
                loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {
                    Text(
                        text = error ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {

                    when (viewMode) {

                        // =========================
                        // LIST MODE
                        // =========================
                        MoneyViewMode.LIST -> {
                            LazyColumn {

                                // ===== BUDGET CARD =====
                                item {
                                    BudgetCard(
                                        totalIncome = totalIncome,
                                        totalOutcome = totalOutcome
                                    )
                                }

                                // ===== EMPTY STATE =====
                                if (moneyList.isEmpty()) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(32.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "Belum ada transaksi",
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                } else {
                                    // ===== TRANSACTION LIST =====
                                    items(moneyList) { money ->
                                        TransactionCard(
                                            title = money.title,
                                            createdAt = money.createdAt,
                                            amount = money.amount,
                                            type = money.type
                                        )
                                    }
                                }
                            }
                        }

                        // =========================
                        // GRAPH MODE
                        // =========================
                        MoneyViewMode.GRAPH -> {
                            GraphicCard(
                            )
                        }
                    }
                }
            }
        }
    }
}




            @RequiresApi(Build.VERSION_CODES.O)
            fun calculateOutcomeStats(
                moneyList: List<MoneyModel>
            ): Triple<Int, Int, Int> {

                val now = OffsetDateTime.now(ZoneId.systemDefault())
                val today = now.toLocalDate()
                val currentWeek =
                    today.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
                val currentMonth = today.monthValue

                var todayTotal = 0
                var weekTotal = 0
                var monthTotal = 0

                moneyList
                    .filter { it.type == "Outcome" }
                    .forEach { money ->

                        val date = OffsetDateTime
                            .parse(money.createdAt)
                            .toLocalDate()

                        if (date == today) {
                            todayTotal += money.amount
                        }

                        if (
                            date.get(
                                WeekFields.of(Locale.getDefault())
                                    .weekOfWeekBasedYear()
                            ) == currentWeek
                        ) {
                            weekTotal += money.amount
                        }

                        if (date.monthValue == currentMonth) {
                            monthTotal += money.amount
                        }
                    }

                return Triple(todayTotal, weekTotal, monthTotal)
            }


            @Composable
            fun GraphicCard(
                today: Int,
                week: Int,
                month: Int
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = "Ringkasan Pengeluaran",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        StatRow(label = "Hari Ini", value = today)
                        StatRow(label = "Minggu Ini", value = week)
                        StatRow(label = "Bulan Ini", value = month)
                    }
                }
            }

            @Composable
            fun StatRow(label: String, value: Int) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(label)
                    Text(
                        text = "Rp $value",
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }



