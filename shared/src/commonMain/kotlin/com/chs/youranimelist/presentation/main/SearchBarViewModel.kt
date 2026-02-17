package com.chs.youranimelist.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.domain.usecase.DeleteSearchHistoryUseCase
import com.chs.youranimelist.domain.usecase.GetSearchHistoryUseCase
import com.chs.youranimelist.domain.usecase.InsertSearchHistoryUseCase
import com.chs.youranimelist.presentation.search.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchBarViewModel(
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase,
): ViewModel(){
    private val _state = MutableStateFlow(SearchBarState())
    val state = _state
        .onStart { getSearchHistory() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _state.value
        )

    private fun getSearchHistory() {
        viewModelScope.launch {
            getSearchHistoryUseCase().collectLatest { list ->
                _state.update { it.copy(searchHistory = list) }
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