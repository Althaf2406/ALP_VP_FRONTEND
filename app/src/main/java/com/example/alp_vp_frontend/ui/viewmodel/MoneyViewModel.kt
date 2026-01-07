package com.example.alp_vp_frontend.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_vp_frontend.data.dto.CreateMoneyRequest
import com.example.alp_vp_frontend.data.dto.UpdateMoneyRequest
import com.example.alp_vp_frontend.data.repository.MoneyRepository
import com.example.alp_vp_frontend.ui.model.MoneyModel
import com.example.alp_vp_frontend.ui.model.from
import com.example.alp_vp_frontend.ui.model.fromList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

class MoneyViewModel(
    private val repository: MoneyRepository
) : ViewModel() {


    // ===============================
    // STATE
    // ===============================
    private val _moneyList = MutableStateFlow<List<MoneyModel>>(emptyList())
    val moneyList: StateFlow<List<MoneyModel>> = _moneyList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _userId = MutableStateFlow<Int?>(null)
    val userId = _userId.asStateFlow()

    init {
        loadMoney()
    }


    // ===============================
    // DERIVED STATE
    // ===============================
    val totalIncome: StateFlow<Int> =
        moneyList
            .map { it.filter { m -> m.type == "Income" }.sumOf { m -> m.amount } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val totalOutcome: StateFlow<Int> =
        moneyList
            .map { it.filter { m -> m.type == "Outcome" }.sumOf { m -> m.amount } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    // ===============================
    // GET ALL MONEY
    // ===============================
    fun loadMoney() {
        viewModelScope.launch {
            _loading.value = true

            try {
                val response = repository.getAllMoney()

                if (response?.isSuccessful == true) {
                    val mapped = withContext(Dispatchers.Default) {
                        MoneyModel.fromList(response?.body()?.data ?: emptyList())
                    }
                    _moneyList.value = mapped
                } else {
                    _error.value = "Failed ${response?.code()}"
                }

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    // ===============================
    // CREATE MONEY
    // ===============================
    fun createMoney(
        title: String,
        description: String,
        amount: Int,
        type: String,
        userId: Int,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {
            _loading.value = true

            try {
                val response = withContext(Dispatchers.IO) {
                    repository.createMoney(
                        CreateMoneyRequest(
                            title = title,
                            description = description,
                            amount = amount,
                            type = type,
                            user_id = userId
                        )
                    )
                }

                if (response.isSuccessful) {
                    response.body()?.data?.let { money ->
                        val model = withContext(Dispatchers.Default) {
                            MoneyModel.from(money)
                        }
                        _moneyList.value = _moneyList.value + model
                    }
                    Log.d("CREATE", "POST money called")

                } else {
                    _error.value = "Failed ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    // ===============================
    // UPDATE MONEY
    // ===============================
    fun updateMoney(
        id: Int,
        title: String,
        description: String,
        amount: Int,
        type: String,
        onSuccess: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val response = repository.updateMoney(
                id,
                UpdateMoneyRequest(
                    title = title,
                    description = description,
                    amount = amount,
                    type = type
                )
            )

            if (response?.isSuccessful == true) {
                loadMoney()
                onSuccess?.invoke()
            } else {
                _error.value = "Failed to update money (${response?.code()})"
            }

            _loading.value = false
        }
    }

    fun clearState() {
        _moneyList.value = emptyList()
        _error.value = null
    }

    // ===============================
    // DELETE MONEY
    // ===============================
    fun deleteMoney(
        id: Int,
        onSuccess: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val response = repository.deleteMoney(id)
            if (response?.isSuccessful == true) {
                loadMoney()
                onSuccess?.invoke()
            } else {
                _error.value = "Failed to delete money (${response?.code()})"
            }

            _loading.value = false
        }
    }
}
