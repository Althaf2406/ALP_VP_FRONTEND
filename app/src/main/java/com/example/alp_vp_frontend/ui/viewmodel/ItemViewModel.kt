package com.example.alp_vp_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.model.* import com.example.alp_vp_frontend.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Data class UiState tetap sama
data class UiState(
    val summary: Summary = Summary(0),
    val allItems: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)


class ItemViewModel (
    private val repository: ItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    // Fungsi loadData() tetap sama

    fun loadData() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val summary = repository.getSummary()
                val items = repository.getAllItems()

                _uiState.update {
                    it.copy(summary = summary, allItems = items, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Koneksi Gagal: ${e.message}")
                }
            }
        }
    }

    // --- CRUD OPERATIONS ---

    fun createNewTask(title: String, description: String?, dateTime: String, difficulty: String) {
        viewModelScope.launch {
            try {
                // Tambahkan 'Z' di akhir string waktu jika belum ada, untuk format ISO 8601 yang ketat
                val formattedDateTime = if (dateTime.endsWith('Z', ignoreCase = true)) dateTime else "${dateTime}Z"

                val newTask = Task(
                    title = title,
                    description = description,
                    dateTime = formattedDateTime, // Menggunakan format yang diperbaiki
                    difficulty = difficulty
                )
                repository.createTask(newTask)
                loadData()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Gagal membuat Task: ${e.message}") }
            }
        }
    }

    fun createNewActivity(title: String, description: String?, startDateTime: String, endDateTime: String) {
        viewModelScope.launch {
            try {
                // Tambahkan 'Z' di akhir string waktu jika belum ada
                val formattedStartDateTime = if (startDateTime.endsWith('Z', ignoreCase = true)) startDateTime else "${startDateTime}Z"
                val formattedEndDateTime = if (endDateTime.endsWith('Z', ignoreCase = true)) endDateTime else "${endDateTime}Z"

                val newActivity = Activity(
                    title = title,
                    description = description,
                    startDateTime = formattedStartDateTime, // Menggunakan format yang diperbaiki
                    endDateTime = formattedEndDateTime
                )
                repository.createActivity(newActivity)
                loadData()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Gagal membuat Activity: ${e.message}") }
            }
        }
    }

    // Fungsi toggleItemCompletion() tetap sama
    fun toggleItemCompletion(item: Item) {
        viewModelScope.launch {
            try {
                if (item.type == "Task") {
                    val taskUpdate = Task(
                        id = item.id,
                        title = item.title,
                        description = item.description,
                        dateTime = item.dateTime!!,
                        isCompleted = !item.isCompleted,
                        difficulty = item.difficulty!!
                    )
                    repository.updateTask(taskUpdate)
                } else if (item.type == "Activity") {
                    val activityUpdate = Activity(
                        id = item.id,
                        title = item.title,
                        description = item.description,
                        startDateTime = item.startDateTime!!,
                        endDateTime = item.endDateTime!!,
                        isCompleted = !item.isCompleted
                    )
                    repository.updateActivity(activityUpdate)
                }
                loadData()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Gagal mengubah status: ${e.message}") }
            }
        }
    }

    // --- FUNGSI BARU: DELETE ---
    fun deleteItem(item: Item) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                // Memeriksa tipe item untuk memanggil fungsi repository yang sesuai
                if (item.type == "Task") {
                    repository.deleteTask(item.id)
                } else if (item.type == "Activity") {
                    repository.deleteActivity(item.id)
                }

                // Muat ulang data untuk memperbarui UI setelah penghapusan
                loadData()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Gagal menghapus ${item.type}: ${e.message}"
                    )
                }
            }
        }
    }
}