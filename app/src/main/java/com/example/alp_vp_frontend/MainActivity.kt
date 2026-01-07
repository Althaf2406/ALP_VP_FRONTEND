package com.example.alp_vp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.alp_vp_frontend.di.AppContainer
import com.example.alp_vp_frontend.di.DefaultAppContainer
import com.example.alp_vp_frontend.ui.theme.ALp_VP_FrontendTheme

// Pastikan file AppNavigation.kt sudah ada (biasanya di package root atau ui)
// Jika merah, coba tekan Alt+Enter untuk import class AppNavigation
import AppNavigation

class MainActivity : ComponentActivity() {

    // Setup Container untuk Dependency Injection (Retrofit, Token, dll)
    lateinit var container: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi Container
        // DefaultAppContainer biasanya butuh context untuk TokenManager
        container = DefaultAppContainer(applicationContext)

        setContent {
            ALp_VP_FrontendTheme {
                // Panggil Navigasi Utama dan lempar container ke dalamnya
                AppNavigation(appContainer = container)
            }
        }
    }
}