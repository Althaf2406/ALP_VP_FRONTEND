package com.example.alp_vp_frontend.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_vp_frontend.data.dto.Focus
import com.example.alp_vp_frontend.ui.viewmodel.FocusViewModel

@Composable
fun FocusView(
    focusViewModel: FocusViewModel,
    onFocusClick: (Focus) -> Unit
) {
    val focusList by focusViewModel.focusList.collectAsState()
    val loading by focusViewModel.isLoading.collectAsState()
    val error by focusViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        focusViewModel.loadFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Focus List",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

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
                    color = Color.Red
                )
            }

            focusList.isEmpty() -> {
                Text(
                    text = "Belum ada focus",
                    color = Color.Gray
                )
            }

            else -> {
                LazyColumn {
                    items(focusList) { focus ->
                        FocusCard(
                            focus = focus,
                            onClick = { onFocusClick(focus) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FocusCard(
    focus: Focus,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Focus ID: ${focus.id}",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Start: ${focus.startTime}"
            )
            Text(
                text = "End: ${focus.endTime}"
            )
        }
    }
}
