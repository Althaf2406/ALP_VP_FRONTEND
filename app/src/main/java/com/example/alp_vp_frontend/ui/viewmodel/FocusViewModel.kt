package com.example.alp_vp_frontend.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.dto.Focus
import com.example.alp_vp_frontend.data.repository.FocusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FocusViewModel(
    private val repository: FocusRepository
) : ViewModel() {

    // ============================
    // STATE
    // ============================
    private val _focusList = MutableStateFlow<List<Focus>>(emptyList())
    val focusList: StateFlow<List<Focus>> = _focusList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // ============================
    // LOAD ALL FOCUS
    // ============================
    fun loadFocus() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getAllFocus()

            result
                .onSuccess {
                    _focusList.value = it.data
                }
                .onFailure {
                    _error.value = it.message
                }

            _isLoading.value = false
        }
    }

    // ============================
    // CREATE FOCUS
    // ============================
    fun createFocus(focus: Focus, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.createFocus(focus)

            result
                .onSuccess {
                    _focusList.value = (_focusList.value + it.data) as List<Focus>
                    onSuccess()
                }
                .onFailure {
                    _error.value = it.message
                }

            _isLoading.value = false
        }
    }

    // ============================
    // DELETE FOCUS
    // ============================
    fun deleteFocus(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.deleteFocus(id)

            result
                .onSuccess {
                    _focusList.value = _focusList.value.filterNot { it.id == id }
                }
                .onFailure {
                    _error.value = it.message
                }

            _isLoading.value = false
        }
    }
}
