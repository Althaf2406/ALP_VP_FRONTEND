package com.example.alp_vp_frontend.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.dto.Phase
import com.example.alp_vp_frontend.data.repository.FocusPhaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FocusPhaseViewModel(
    private val repository: FocusPhaseRepository
) : ViewModel() {

    // ============================
    // STATE
    // ============================
    private val _phases = MutableStateFlow<List<Phase>>(emptyList())
    val phases: StateFlow<List<Phase>> = _phases

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // ============================
    // LOAD PHASES BY FOCUS
    // ============================
    fun loadByFocus(focusId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                _phases.value = repository.getByFocusId(focusId)
            } catch (e: Exception) {
                _error.value = "Gagal memuat phase"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ============================
    // CREATE PHASE
    // ============================
    fun createPhase(
        focusId: Int,
        type: String,
        duration: Int
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val phase = repository.createPhase(
                    focusId = focusId,
                    type = type,
                    duration = duration
                )

                if (phase != null) {
                    _phases.value = _phases.value + phase
                } else {
                    _error.value = "Gagal menambahkan phase"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ============================
    // UPDATE PHASE
    // ============================
    fun updatePhase(
        phaseId: Int,
        updates: Map<String, Any>
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val updated = repository.updatePhase(phaseId, updates)
                if (updated != null) {
                    _phases.value = _phases.value.map {
                        if (it.id == phaseId) updated else it
                    }
                } else {
                    _error.value = "Gagal memperbarui phase"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ============================
    // DELETE PHASE
    // ============================
    fun deletePhase(phaseId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val success = repository.deletePhase(phaseId)
                if (success) {
                    _phases.value = _phases.value.filterNot { it.id == phaseId }
                } else {
                    _error.value = "Gagal menghapus phase"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
