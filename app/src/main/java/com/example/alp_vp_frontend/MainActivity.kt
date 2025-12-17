package com.example.alp_vp_frontend

import AppNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alp_vp_frontend.data.repository.ItemRepositoryImpl
import com.example.alp_vp_frontend.data.service.ItemApiService
import com.example.alp_vp_frontend.di.AppContainer
import com.example.alp_vp_frontend.di.DefaultAppContainer
import com.example.alp_vp_frontend.navigation.Screen
import com.example.alp_vp_frontend.ui.screen.AddActivityScreen
import com.example.alp_vp_frontend.ui.screen.AddTaskScreen
import com.example.alp_vp_frontend.ui.screen.SummaryScreen
import com.example.alp_vp_frontend.ui.theme.ALp_VP_FrontendTheme // Pastikan nama Theme Anda benar
import com.example.alp_vp_frontend.ui.viewmodel.ItemViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



// File: MainActivity.kt

class MainActivity : ComponentActivity() {


    lateinit var container: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        container = DefaultAppContainer()

        setContent {
            ALp_VP_FrontendTheme {
                // Meneruskan kontainer ke Navigasi
                AppNavigation(appContainer = container)
            }
        }
    }
}
//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()
//
//    // --- DI MANUAL: Buat Repository dan Service di sini ---
//
//    // 1. Buat Retrofit Instance (Service)
//    val retrofit = remember {
//        // Pastikan BASE_URL dan ConverterFactory benar
//        Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:3000/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//    val itemApiService = remember { retrofit.create(ItemApiService::class.java) }
//
//    // 2. Buat Repository Instance
//    val repository = remember { ItemRepositoryImpl(itemApiService) } // ASUMSI ItemRepositoryImpl adalah implementasi Anda
//
//    // 3. Buat Factory
//    val viewModelFactory = remember { ItemViewModelFactory(repository) }
//    // --------------------------------------------------------
//
//    NavHost(navController = navController, startDestination = Screen.Summary.route) {
//
//        composable(Screen.Summary.route) {
//            // Menggunakan viewModel() dengan factory
//            SummaryScreen(
//                navController = navController,
//                viewModel = viewModel(factory = viewModelFactory) // <- INI KUNCINYA
//            )
//        }
//
//        composable(Screen.AddTask.route) {
//            AddTaskScreen(
//                navController = navController,
//                viewModel = viewModel(factory = viewModelFactory)
//            )
//        }
//
//        composable(Screen.AddActivity.route) {
//            AddActivityScreen(
//                navController = navController,
//                viewModel = viewModel(factory = viewModelFactory)
//            )
//        }
//    }
//}