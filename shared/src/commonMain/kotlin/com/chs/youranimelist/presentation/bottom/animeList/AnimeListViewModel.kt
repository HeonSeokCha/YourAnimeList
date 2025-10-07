package com.chs.youranimelist.presentation.bottom.animeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.domain.usecase.GetSavedAnimeListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeListViewModel(
    private val getYourAnimeListUseCase: GetSavedAnimeListUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AnimeListState> = MutableStateFlow(AnimeListState())
    val state = _state
        .onStart { getAnimeList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )
    private val _effect: Channel<AnimeListEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: AnimeListIntent) {
        when (intent) {
            is AnimeListIntent.ClickAnime -> {
                _effect.trySend(
                    AnimeListEffect.NavigateAnimeDetail(
                        id = intent.id,
                        idMal = intent.idMal
                    )
                )
            }
        }
    }

    private fun getAnimeList() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getYourAnimeListUseCase().collect { animeInfo ->
                if (animeInfo.isEmpty()) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isEmpty = true
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            list = animeInfo
                        )
                    }
                }
            }
        }
    }
}