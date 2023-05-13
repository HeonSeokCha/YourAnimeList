package com.chs.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chs.domain.usecase.GetSearchHistoryUseCase
import com.chs.domain.usecase.InsertSearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        getSearchHistory()
    }

    private fun getSearchHistory() {
        viewModelScope.launch {
            getSearchHistoryUseCase().collect { historyList ->
                _state.update {
                    it.copy(
                        searchHistoryList = historyList
                    )
                }
            }
        }
    }

    fun insertSearchHistory(title: String) {
        viewModelScope.launch {
            insertSearchHistoryUseCase(title)
        }
    }
}
