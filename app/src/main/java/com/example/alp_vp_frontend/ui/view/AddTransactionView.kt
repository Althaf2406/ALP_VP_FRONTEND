package com.example.alp_vp_frontend.ui.view

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_vp_frontend.ui.viewmodel.AuthViewModel
import com.example.alp_vp_frontend.ui.viewmodel.MoneyViewModel


@Composable
fun AddTransactionView(
    moneyViewModel: MoneyViewModel,
    authViewModel: AuthViewModel,
    onSuccess: () -> Unit
){
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Outcome") } // default
    var amountText by remember { mutableStateOf("") }


    val loading by moneyViewModel.loading.collectAsState()
    val error by moneyViewModel.error.collectAsState()
    val userId by authViewModel.userId.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Tambah Transaksi",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = amountText,
            onValueChange = {
                if (it.all { c -> c.isDigit() }) {
                    amountText = it
                }
            },
            label = { Text("Jumlah") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ======================
        // TYPE SELECTOR
        // ======================
        Row {
            listOf("Income", "Outcome").forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { type = option }
                ) {
                    RadioButton(
                        selected = type == option,
                        onClick = { type = option }
                    )
                    Text(option)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                android.util.Log.d("BTN", "BUTTON CLICKED")

                val amount = amountText.toIntOrNull()
                val uid = userId

                Log.d(
                    "BTN",
                    "title='${title}', amountText='${amountText}', amount=$amount, userId=$uid"
                )

                if (title.isBlank()) {
                    Log.d("BTN", "FAIL: title blank")
                    return@Button
                }

                if (amount == null || amount <= 0) {
                    Log.d("BTN", "FAIL: amount invalid")
                    return@Button
                }

                if (uid == null) {
                    Log.d("BTN", "FAIL: userId null")
                    return@Button
                }
                if (amount == null || uid == null || title.isBlank()) {
                    android.util.Log.d("BTN", "VALIDATION FAILED")
                    return@Button
                }

                android.util.Log.d("BTN", "CALLING createMoney")

                moneyViewModel.createMoney(
                    title = title,
                    description = description,
                    amount = amount,
                    type = type,
                    userId = uid
                ) {
                    onSuccess()
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (loading) "Menyimpan..." else "Simpan")
        }

        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddTransactionViewPreview() {

}