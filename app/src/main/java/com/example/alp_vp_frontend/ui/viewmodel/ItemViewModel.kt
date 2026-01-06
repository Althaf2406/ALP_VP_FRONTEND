package com.example.alp_vp_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.model.*
import com.example.alp_vp_frontend.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Data class UiState
data class UiState(
    val summary: Summary = Summary(0),
    val allItems: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ItemViewModel(
    private val repository: ItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        // Hanya tampilkan loading jika data benar-benar kosong (awal buka)
        // Ini mencegah layar berkedip saat update/delete
        if (_uiState.value.allItems.isEmpty()) {
            _uiState.update { it.copy(isLoading = true, error = null) }
        }

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
                val formattedDateTime = if (dateTime.endsWith('Z', ignoreCase = true)) dateTime else "${dateTime}Z"
                val newTask = Task(
                    title = title,
                    description = description,
                    dateTime = formattedDateTime,
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
                val formattedStartDateTime = if (startDateTime.endsWith('Z', ignoreCase = true)) startDateTime else "${startDateTime}Z"
                val formattedEndDateTime = if (endDateTime.endsWith('Z', ignoreCase = true)) endDateTime else "${endDateTime}Z"
                val newActivity = Activity(
                    title = title,
                    description = description,
                    startDateTime = formattedStartDateTime,
                    endDateTime = formattedEndDateTime
                )
                repository.createActivity(newActivity)
                loadData()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Gagal membuat Activity: ${e.message}") }
            }
        }
    }

    fun toggleItemCompletion(item: Item) {
        viewModelScope.launch {
            // 1. OPTIMISTIC UPDATE: Ubah tampilan UI DULUAN sebelum server merespon
            _uiState.update { state ->
                val updatedList = state.allItems.map {
                    if (it.id == item.id) it.copy(isCompleted = !it.isCompleted) else it
                }
                state.copy(allItems = updatedList)
            }

            try {
                // 2. Baru request ke server
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
                // 3. Update summary (persen) di background, tanpa reload list item
                val newSummary = repository.getSummary()
                _uiState.update { it.copy(summary = newSummary) }

            } catch (e: Exception) {
                // Rollback jika error
                loadData()
            }
        }
    }

    // --- FUNGSI DELETE SUPER CEPAT (INSTANT) ---
    fun deleteItem(item: Item) {
        // 1. LANGSUNG HAPUS DARI UI (Memori Aplikasi)
        // Kita tidak menunggu server. Kita langsung buang item dari list yang ada di layar.
        _uiState.update { currentState ->
            val newList = currentState.allItems.filter { it.id != item.id }
            currentState.copy(allItems = newList)
        }

        // 2. Baru jalankan proses hapus database di background
        viewModelScope.launch {
            try {
                if (item.type == "Task") {
                    repository.deleteTask(item.id)
                } else if (item.type == "Activity") {
                    repository.deleteActivity(item.id)
                }

                // 3. Update Persentase (Summary) saja
                // PENTING: JANGAN panggil loadData() di sini untuk list item!
                // Karena list item sudah kita update manual di langkah no 1.
                val newSummary = repository.getSummary()
                _uiState.update { it.copy(summary = newSummary) }

            } catch (e: Exception) {
                // Jika ternyata gagal hapus (internet mati), kembalikan data (Rollback)
                _uiState.update { it.copy(error = "Gagal menghapus: ${e.message}") }
                loadData() // Load ulang data asli agar item muncul lagi
            }
        }
    }
}