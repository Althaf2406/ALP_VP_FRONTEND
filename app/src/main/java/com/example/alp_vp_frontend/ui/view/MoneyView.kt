package com.example.alp_vp_frontend.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alp_vp_frontend.data.repository.AuthRepository
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel

@Composable
fun MoneyView(
    moneyViewModel: MoneyViewModel,
    authViewModel: AuthViewModel
) {
    val moneyList by moneyViewModel.moneyList.collectAsState()
    val loading by moneyViewModel.loading.collectAsState()
    val error by moneyViewModel.error.collectAsState()

    // ⬇️ derived state dari ViewModel
    val totalIncome by moneyViewModel.totalIncome.collectAsState()
    val totalOutcome by moneyViewModel.totalOutcome.collectAsState()


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
            }
        }
    }
}

