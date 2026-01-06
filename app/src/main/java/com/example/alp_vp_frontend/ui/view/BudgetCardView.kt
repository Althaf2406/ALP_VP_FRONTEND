package com.example.alp_vp_frontend.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_vp_frontend.ui.model.MoneyModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.NumberFormat
import java.util.*


@Composable
fun BudgetCard(
    modifier: Modifier = Modifier,
    totalIncome: Int,
    totalOutcome: Int
) {
    val limitAnggaran = totalIncome
    val budgetUsed = totalOutcome
    val percentage = if (limitAnggaran == 0) 0f
    else (budgetUsed.toFloat() / limitAnggaran.toFloat())

    val percentageText = (percentage * 100).toInt()

    val dailyBudget = (limitAnggaran * 0.5) / 30

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            // Judul
            Text(
                text = "Anggaran Desember",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Angka + Persentase
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        text = "Rp. ${formatRupiah(budgetUsed)}",
                        color = Color(0xFF117A37),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "/ Rp. ${formatRupiah(limitAnggaran)}",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Text(
                    text = "${percentageText}%",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = percentage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Color(0xFF117A37),
                trackColor = Color(0xFFEAEAEA)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Daily Budget
            Text(
                text = "Daily budget - (Rp. ${formatRupiah(dailyBudget.toInt())})",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}


fun formatRupiah(value: Int): String {
    return "%,d".format(value).replace(",", ".")
}

@Composable
@Preview(showBackground = true)
fun BudgetCardPreview() {
    BudgetCard(
        totalIncome = 8000000,
        totalOutcome = 5120000
    )
}



