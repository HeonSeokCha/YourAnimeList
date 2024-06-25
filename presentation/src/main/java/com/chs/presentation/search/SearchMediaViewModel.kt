package com.chs.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.presentation.UiConst
import com.chs.domain.usecase.GetAnimeSearchResultUseCase
import com.chs.domain.usecase.GetCharaSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchMediaViewModel @Inject constructor(
    private val searchAnimeUseCase: GetAnimeSearchResultUseCase,
    private val searchCharaUseCase: GetCharaSearchResultUseCase
) : ViewModel() {

    var state: SearchMediaState by mutableStateOf(SearchMediaState())
        private set

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ChangeSearchQuery -> {
                state = state.copy(
                    query = event.query
                )
            }

            is SearchEvent.GetSearchAnimeResult -> {
                state = state.copy(
                    searchAnimeResultPaging = searchAnimeUseCase(state.query!!)
                        .cachedIn(viewModelScope)
                )
            }

            is SearchEvent.GetSearchCharaResult -> {
                state = state.copy(
                    searchCharaResultPaging = searchCharaUseCase(state.query!!)
                        .cachedIn(viewModelScope)
                )
            }
        }
    }
}