package com.example.alp_vp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alp_vp_frontend.data.container.AppContainer
import com.example.alp_vp_frontend.ui.route.Route
import com.example.alp_vp_frontend.ui.theme.ALP_VP_FrontendTheme
import com.example.alp_vp_frontend.ui.view.ActivityView
import com.example.alp_vp_frontend.ui.view.DailyScheduleView
import com.example.alp_vp_frontend.ui.view.HomeScreen
import com.example.alp_vp_frontend.ui.view.LoginView
import com.example.alp_vp_frontend.ui.viewModelFactory.ViewModelFactory

// 1. UBAH ITEM BOTTOM BAR: Hapus 'Home', karena Home adalah dashboard
sealed class BottomBarItem(val route: String, val title: String, val icon: ImageVector) {
    object Calendar : BottomBarItem(Route.ActivityCalendar.screenName, "Calendar", Icons.Filled.CalendarMonth)
    object Timer : BottomBarItem(Route.FocusTimer.screenName, "Timer", Icons.Filled.Timer)
    object Profile : BottomBarItem(Route.Profile.screenName, "Profile", Icons.Filled.Person)
    // Anda bisa tambahkan Finance di sini jika perlu
}

class MainActivity : ComponentActivity() {
    private lateinit var appContainer: AppContainer
    private lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // Anda bisa mengaktifkan ini nanti
        appContainer = AppContainer()
        viewModelFactory = ViewModelFactory(appContainer)
        setContent {
            ALP_VP_FrontendTheme {
                MainAppScreen(viewModelFactory = viewModelFactory)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(viewModelFactory: ViewModelFactory) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // 2. LOGIKA BARU: Tentukan apakah BottomBar harus ditampilkan
    // BottomBar hanya muncul jika halaman saat ini BUKAN Home (dashboard), Login, atau Detail
    val showBottomBar = currentDestination?.route !in listOf(
        Route.Home.screenName,
        Route.Login.screenName,
        Route.DailySchedule.screenName
    )

    // Tombol kembali muncul jika halaman saat ini BUKAN halaman awal (Home/dashboard)
    val showBackButton = currentDestination?.route != Route.Home.screenName

    Scaffold(
        topBar = {
            // Kita bisa membuat TopBar lebih pintar, tapi untuk sekarang ini cukup
            TopAppBar(
                title = { /* Judul */ },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                // 3. BOTTOM BAR YANG DISEMPURNAKAN
                val items = listOf(
                    BottomBarItem.Calendar,
                    BottomBarItem.Timer,
                    BottomBarItem.Profile
                )
                NavigationBar {
                    items.forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            viewModelFactory = viewModelFactory,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModelFactory: ViewModelFactory,
    modifier: Modifier = Modifier
) {
    // 4. NAVHOST TETAP DIMULAI DARI HOME (DASHBOARD)
    NavHost(
        navController = navController,
        startDestination = Route.Home.screenName,
        modifier = modifier
    ) {
        composable(Route.Login.screenName) {
            LoginView(
                onLoginSuccess = { navController.navigate(Route.Home.screenName) },
                viewModel = viewModel(factory = viewModelFactory)
            )
        }

        composable(Route.Home.screenName) {
            HomeScreen(navController = navController)
        }

        composable(Route.ActivityCalendar.screenName) {
            ActivityView(
                navController = navController,
                viewModel = viewModel(factory = viewModelFactory)
            )
        }

        composable(
            route = Route.DailySchedule.screenName,
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            if (date != null) {
                DailyScheduleView(
                    navController = navController,
                    viewModel = viewModel(factory = viewModelFactory),
                    date = date
                )
            }
        }

        // Contoh untuk 3 halaman lainnya
        composable(Route.Finance.screenName) { Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) { Text("Finance Screen Content") } }
        composable(Route.FocusTimer.screenName) { Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) { Text("Focus Timer Screen Content") } }
        composable(Route.Profile.screenName) { Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) { Text("Profile Screen Content") } }
    }
}
