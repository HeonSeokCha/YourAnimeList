package com.chs.youranimelist.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.youranimelist.domain.usecase.GetAnimeSearchResultUseCase
import com.chs.youranimelist.domain.usecase.GetCharaSearchResultUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
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

    private val _effect: Channel<SearchEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.ClickAnime -> _effect.trySend(
                SearchEffect.NavigateAnimeDetail(
                    id = intent.id,
                    idMal = intent.idMal
                )
            )

            is SearchIntent.ClickChara -> _effect.trySend(
                SearchEffect.NavigateCharaDetail(id = intent.id)
            )

            SearchIntent.LoadAnime -> _state.update { it.copy(isAnimeLoading = true) }
            SearchIntent.LoadCompleteAnime -> _state.update { it.copy(isAnimeLoading = false) }
            SearchIntent.LoadChara -> _state.update { it.copy(isCharaLoading = true) }
            SearchIntent.LoadCompleteChara -> _state.update { it.copy(isCharaLoading = false) }

            is SearchIntent.OnChangeSearchQuery -> {
                _state.update {
                    it.copy(
                        searchAnimeResultPaging = searchAnimeUseCase(intent.query)
                            .cachedIn(viewModelScope),
                        searchCharaResultPaging = searchCharaUseCase(intent.query)
                            .cachedIn(viewModelScope)
                    )
                }
            }

            is SearchIntent.OnChangeTabIdx -> _state.update { it.copy(selectedTabIdx = intent.idx) }
            SearchIntent.OnError -> _effect.trySend(SearchEffect.ShowErrorSnackBar)
        }
    }
}