package com.example.alp_vp_frontend.ui.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alp_vp_frontend.data.container.AppContainer
import com.example.alp_vp_frontend.ui.viewmodel.ActivityViewModel
import com.example.alp_vp_frontend.ui.viewmodel.LoginViewModel

class ViewModelFactory(private val appContainer: AppContainer) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Jika ada yang minta "Otak" untuk ActivityView...
        if (modelClass.isAssignableFrom(ActivityViewModel::class.java)) {
            // ...ambil 'activityRepository' dari Pabrik, lalu buat ViewModel-nya.
            return ActivityViewModel(appContainer.activityRepository) as T
        }

        // Jika ada yang minta "Otak" untuk LoginView...
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            // ...ambil 'userRepository' dari Pabrik.
            // PERHATIAN: Baris ini akan crash karena TODO() di AppContainer.
            // Ini disengaja agar kita fokus pada ActivityView dulu.
            return LoginViewModel(appContainer.userRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
