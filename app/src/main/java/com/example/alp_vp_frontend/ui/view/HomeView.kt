//package com.example.alp_vp_frontend.ui.view
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.filled.CalendarMonth
//import androidx.compose.material.icons.filled.MonetizationOn
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Timer
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Timer
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.navigation.NavController
//import com.example.alp_vp_frontend.ui.route.Route
//
//@Composable
//fun HomeView(
//    navController: NavController
//) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Welcome!",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 48.dp)
//        )
//
//        LargeMenuButton(
//            text = "Activity Management",
//            icon = Icons.Default.CalendarMonth
//        ) {
//            navController.navigate(Route.ActivityCalendar.screenName)
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        LargeMenuButton(
//            text = "Finance Tracker",
//            icon = Icons.Default.MonetizationOn
//        ) {
//            navController.navigate(Route.Finance.screenName)
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        LargeMenuButton(
//            text = "Focus Timer",
//            icon = Icons.Default.Timer
//        ) {
//            navController.navigate(Route.Timer.screenName)
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        LargeMenuButton(
//            text = "Profile",
//            icon = Icons.Default.Person
//        ) {
//            navController.navigate(Route.Profile.screenName)
//        }
//    }
//}
//
//@Composable
//private fun LargeMenuButton(
//    text: String,
//    icon: ImageVector,
//    onClick: () -> Unit
//) {
//    Button(
//        onClick = onClick,
//        modifier = Modifier
//            .fillMaxWidth(0.8f)
//            .height(80.dp),
//        shape = MaterialTheme.shapes.medium
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Icon(
//                imageVector = icon,
//                contentDescription = text,
//                modifier = Modifier.size(40.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(
//                text = text,
//                fontSize = 18.sp
//            )
//        }
//    }
//}
