package com.example.alp_vp_frontend.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alp_vp_frontend.ui.route.Route

@Composable
fun HomeScreen(
    navController: NavController
) {
    // Column utama yang menengahkan semua konten
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // Tombol 1: Activity Management (menuju kalender)
        LargeMenuButton(
            text = "Activity Management",
            icon = Icons.Default.CalendarMonth,
            onClick = {
                navController.navigate(Route.ActivityCalendar.screenName)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Tombol 2: Finance Tracker (contoh)
        LargeMenuButton(
            text = "Finance Tracker",
            icon = Icons.Default.MonetizationOn,
            onClick = {
                navController.navigate(Route.Finance.screenName)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Tombol 3: Focus Timer (contoh)

        Spacer(modifier = Modifier.height(20.dp))

        // Tombol 4: Profile (contoh)
        LargeMenuButton(
            text = "Profile",
            icon = Icons.Default.Person,
            onClick = {
                navController.navigate(Route.Profile.screenName)
            }
        )
    }
}

// Composable terpisah untuk tombol besar agar kode lebih rapi
@Composable
fun LargeMenuButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f) // Lebar tombol 80% dari layar
            .height(80.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, fontSize = 18.sp)
        }
    }
}
