package com.example.alp_vp_frontend.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.dto.Data
import com.example.alp_vp_frontend.data.dto.NewActivity
import com.example.alp_vp_frontend.data.dto.ResponsActivityById
import com.example.alp_vp_frontend.data.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
// ... import lainnya
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate //1. IMPORT LocalDate

class ActivityViewModel(
    private val repository: ActivityRepository
) : ViewModel() {

    // --- STATE FLOW ---

    // 2. UBAH STATE INI untuk mengelompokkan aktivitas berdasarkan tanggal
    private val _activitiesByDate = MutableStateFlow<Map<LocalDate, List<Data>>>(emptyMap())
    val activitiesByDate: StateFlow<Map<LocalDate, List<Data>>> = _activitiesByDate.asStateFlow()

    // ... state lainnya bisa tetap sama ...
    private val _detailActivityState = MutableStateFlow<ResponsActivityById?>(null)
    val detailActivityState: StateFlow<ResponsActivityById?> = _detailActivityState.asStateFlow()

    private val _uiState = MutableStateFlow<String>("Idle")
    val uiState: StateFlow<String> = _uiState.asStateFlow()


    // --- FUNGSI-FUNGSI ---

    // 3. MODIFIKASI FUNGSI INI
    fun getAllActivities() {
        viewModelScope.launch {
            _uiState.value = "Loading"
            val response = repository.getAllActivities()
            if (response != null) {
                // Kelompokkan data berdasarkan tanggal
                _activitiesByDate.value = response.data.groupBy {
                    // Ubah string "YYYY-MM-DD" menjadi objek LocalDate
                    LocalDate.parse(it.start_date)
                }
                _uiState.value = "Success"
            } else {
                _uiState.value = "Error"
            }
        }
    }

    // ... fungsi lainnya tetap sama ...
    fun getActivityById(id: Int) { /* ... */ }
    fun createActivity(newActivity: NewActivity) { /* ... */ }
    fun updateActivity(id: Int, updates: Map<String, Any>) { /* ... */ }
    fun deleteActivity(id: Int) { /* ... */ }
}
