//package com.example.labweek7.ui.viewmodel
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.labweek7.data.container.WeatherContainer
//import com.example.labweek7.data.repository.WeatherAppServerRepository
//import com.example.labweek7.ui.model.WeatherDisplay
//import com.example.labweek7.ui.model.from
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//
//data class Soal1WeatherUiState(
//    val isLoading: Boolean = false,
//    val wthrData: WeatherDisplay? = null,
//    val eMsg: String? = null
//)
//
//class Soal1WeatherViewModel(
//    val repository: WeatherAppServerRepository = WeatherContainer().repository
//) : ViewModel() {
//
//    var searchQuery by mutableStateOf("")
//    var uState by mutableStateOf(Soal1WeatherUiState())
//    fun updateSearchQuery(query: String) {
//        searchQuery = query
//    }
//
//    fun fetchWeather(apiKey: String = "2639266dc96f22afed5d32ceff58ead4") {
//        if (searchQuery.isBlank()) {
//            uState = uState.copy(eMsg = "City name cannot be empty")
//            return
//        }
//
//        uState = Soal1WeatherUiState(isLoading = true)
//
//        viewModelScope.launch {
//            try {
//                val response = repository.getWeather(searchQuery, apiKey)
//                println("🌐 Response dari API: $response") // 👈 tambahkan ini
//
//                if (response != null) {
//                    uState = Soal1WeatherUiState(
//                        isLoading = false,
//                        wthrData = WeatherDisplay.from(response)
//                    )
//                } else {
//                    uState = Soal1WeatherUiState(
//                        isLoading = false,
//                        eMsg = "No data found (null response)"
//                    )
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                uState = Soal1WeatherUiState(
//                    isLoading = false,
//                    eMsg = e.message ?: "Unexpected error"
//                )
//            }
//        }
//    }
//}
