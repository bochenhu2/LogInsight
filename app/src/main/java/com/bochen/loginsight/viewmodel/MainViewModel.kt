package com.bochen.loginsight.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bochen.loginsight.data.model.LogDetail
import com.bochen.loginsight.data.repository.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: LogRepository): ViewModel() {

    data class MainUiState(
        val logList: List<LogDetail> = emptyList(),
        val keyword: String = "",
        val selectedLevel: String? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    /*var logList by mutableStateOf(emptyList<LogDetail>())
        private set
    var keyword by mutableStateOf("")
        private set*/

    //private val repository = LogRepository()

    init {
        observeLogs()
    }

    /*fun loadLogs(){
        viewModelScope.launch {
            _uiState.update {
                    currentState ->
                currentState.copy(isLoading = true, errorMessage = null)
            }
            try {
                val logs = repository.getAllLogs()
                _uiState.update {
                        currentState ->
                    currentState.copy(logList = logs)
                }
            } catch (e : Exception){
                _uiState.update {
                    it.copy(errorMessage = e.message ?: "Fail to load logs")
                }
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }

        }
    }*/

    private fun observeLogs(){
        viewModelScope.launch {
            _uiState.update {
                    currentState ->
                currentState.copy(isLoading = true, errorMessage = null)
            }
            try {
                repository.observeLogs().collect(){
                    logs ->
                    _uiState.update {
                            it.copy(logList = logs,
                                isLoading = false)
                    }
                }
            } catch (e : Exception){
                _uiState.update {
                    it.copy(isLoading = false,
                        errorMessage = e.message ?: "Fail to load logs")
                }
            }
        }
    }

    fun addLog(log: LogDetail){
        viewModelScope.launch {
            repository.addLog(log)
        }
    }

    fun updateKeyword(keyword: String){
        //this.keyword = keyword
        _uiState.update {
            currentState ->
            currentState.copy(keyword = keyword)
        }
    }

    fun refreshLogs(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val result = repository.refreshLogs()

            result.onFailure {
                exception ->
                _uiState.update {
                    it.copy(
                        errorMessage = exception.message ?: "Failed to refresh logs"
                    )
                }
            }

            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }
}