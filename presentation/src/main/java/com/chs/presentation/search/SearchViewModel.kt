package com.chs.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.domain.usecase.GetAnimeSearchResultUseCase
import com.chs.domain.usecase.GetCharaSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchAnimeUseCase: GetAnimeSearchResultUseCase,
    private val searchCharaUseCase: GetCharaSearchResultUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SearchState()
        )

    private val _event: Channel<SearchEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnChangeSearchQuery -> {
                _state.update {
                    it.copy(
                        searchAnimeResultPaging = searchAnimeUseCase(event.query)
                            .cachedIn(viewModelScope),
                        searchCharaResultPaging = searchCharaUseCase(event.query)
                            .cachedIn(viewModelScope)
                    )
                }
            }

            is SearchEvent.Click.TabIdx -> {
                _state.update {
                    it.copy(
                        selectedTabIdx = event.idx
                    )
                }
            }

            SearchEvent.OnError -> {
                viewModelScope.launch {
                    _event.send(SearchEvent.OnError)
                }
            }

            else -> Unit
        }
    }
}