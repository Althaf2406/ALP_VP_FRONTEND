package com.example.alp_vp_frontend.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.alp_vp_frontend.data.model.Item

@Composable
fun ItemCard(
    item: Item,
    onToggleComplete: (Item) -> Unit,
    onDelete: (Item) -> Unit // <-- FUNGSI BARU: Untuk menghapus item
) {

    val containerColor = when {
        item.isCompleted -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        item.type == "Task" && item.difficulty == "Hard" -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        else -> MaterialTheme.colorScheme.surface
    }

    val iconColor = when {
        item.isCompleted -> MaterialTheme.colorScheme.primary
        item.type == "Task" && item.difficulty == "Hard" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondary
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 8.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kolom Konten Utama (Clickable untuk Toggle)
            Row(
                modifier = Modifier
                    .weight(1f) // Ambil sisa ruang
                    .clickable { onToggleComplete(item) }
                    .padding(end = 8.dp), // Padding tambahan agar tidak terlalu dekat dengan tombol delete
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon status di kiri (dipindah ke sini agar dekat dengan toggle)
                Icon(
                    imageVector = when {
                        item.isCompleted -> Icons.Default.CheckCircle
                        item.type == "Task" && item.difficulty == "Hard" -> Icons.Default.Warning
                        else -> Icons.Default.Info
                    },
                    contentDescription = if (item.isCompleted) "Completed" else item.difficulty ?: item.type,
                    tint = iconColor,
                    modifier = Modifier.size(28.dp).padding(end = 8.dp)
                )

                // Detail Teks
                Column(modifier = Modifier.weight(1f)) {
                    // Judul
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textDecoration = if (item.isCompleted) TextDecoration.LineThrough else null,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Waktu dan Tipe
                    val timeDisplay = when (item.type) {
                        "Task" -> "Deadline: ${item.dateTime ?: "N/A"}"
                        "Activity" -> "Mulai: ${item.startDateTime ?: "N/A"}"
                        else -> ""
                    }

                    Text(
                        text = "$timeDisplay (${item.type})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Tombol Hapus di Kanan
            IconButton(
                onClick = { onDelete(item) }, // <-- Panggil fungsi onDelete
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus Item",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}