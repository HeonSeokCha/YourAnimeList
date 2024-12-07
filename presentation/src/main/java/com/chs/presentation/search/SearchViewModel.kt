package com.chs.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.domain.usecase.GetAnimeSearchResultUseCase
import com.chs.domain.usecase.GetCharaSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
            _state.value
        )

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnChangeSearchQuery -> {
                _state.update {
                    it.copy(
                        query = event.query,
                        searchAnimeResultPaging = searchAnimeUseCase(event.query).cachedIn(
                            viewModelScope
                        ),
                        searchCharaResultPaging = searchCharaUseCase(event.query).cachedIn(
                            viewModelScope
                        )
                    )
                }
            }

            is SearchEvent.GetSearchAnimeResult -> {
                _state.update {
                    it.copy(
                        searchAnimeResultPaging = searchAnimeUseCase(it.query!!)
                            .cachedIn(viewModelScope)
                    )
                }
            }

            is SearchEvent.GetSearchCharaResult -> {
                _state.update {
                    it.copy(
                        searchCharaResultPaging = searchCharaUseCase(it.query!!)
                            .cachedIn(viewModelScope)
                    )
                }
            }

            is SearchEvent.OnTabSelected -> {
                _state.update {
                    it.copy(
                        selectedTabIdx = event.idx
                    )
                }
            }

            else -> Unit
        }
    }
}