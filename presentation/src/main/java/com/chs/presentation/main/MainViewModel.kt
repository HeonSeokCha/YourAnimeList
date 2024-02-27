package com.chs.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.DeleteSearchHistoryUseCase
import com.chs.domain.usecase.GetSearchHistoryUseCase
import com.chs.domain.usecase.InsertSearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        getSearchHistory()
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    searchHistory = getSearchHistoryUseCase()
                )
            }
        }
    }

    fun insertSearchHistory(title: String) {
        viewModelScope.launch {
            insertSearchHistoryUseCase(title)
        }
    }

    fun deleteSearchHistory(title: String) {
        viewModelScope.launch {
            deleteSearchHistoryUseCase(title)
            _state.update {
                it.copy(
                    searchHistory = it.searchHistory.toMutableList().apply {
                        this.remove(title)
                    }
                )
            }
        }
    }
}