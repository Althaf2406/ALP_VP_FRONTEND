package com.example.alp_vp_frontend.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_vp_frontend.ui.model.MoneyModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*
import com.example.alp_vp_frontend.R

@Composable
fun TransactionCard(
    modifier: Modifier = Modifier,
    title: String,
    createdAt: String,
    amount: Int,
    type: String,     // "Income" / "Outcome"
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // ============================
            // LEFT SIDE : ICON + TEXT
            // ============================
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ICON BOX
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF7D9D9)),   // merah muda soft seperti di gambar
                    contentAlignment = Alignment.Center
                ) {

                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = createdAt,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            // ============================
            // RIGHT SIDE : AMOUNT
            // ============================
            val formattedAmount = "Rp. " + "%,d".format(amount).replace(",", ".")

            val amountColor = if (type == "Income") Color(0xFF00AF2F) else Color(0xFFD83A3A)
            val sign = if (type == "Income") "+" else "-"

            Text(
                text = "$sign$formattedAmount",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TransactionCardPreview() {
    TransactionCard(
        modifier = Modifier,
        title = "Kafe",
        createdAt = "12:00",
        amount = 10000,
        type = "Outcome",
    )
}