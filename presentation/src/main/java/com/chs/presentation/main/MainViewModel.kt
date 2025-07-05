package com.chs.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.DeleteSearchHistoryUseCase
import com.chs.domain.usecase.GetRecentGenresTagUseCase
import com.chs.domain.usecase.GetSearchHistoryUseCase
import com.chs.domain.usecase.InsertSearchHistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase,
    private val getRecentGenresTagUseCase: GetRecentGenresTagUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val state = _state
        .onStart {
            getRecentGenresTagUseCase()
            getSearchHistory()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    private fun getSearchHistory() {
        viewModelScope.launch {
            getSearchHistoryUseCase().collectLatest { list ->
                _state.update { list }
            }
        }
    }

    fun insertSearchHistory(title: String) {
        viewModelScope.launch { insertSearchHistoryUseCase(title) }
    }

    fun deleteSearchHistory(title: String) {
        viewModelScope.launch { deleteSearchHistoryUseCase(title) }
    }
}