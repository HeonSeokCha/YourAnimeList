package com.chs.presentation.animeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.GetSavedAnimeListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AnimeListViewModel(
    private val getYourAnimeListUseCase: GetSavedAnimeListUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AnimeListState> = MutableStateFlow(AnimeListState())
    val state = _state
        .onStart { getAnimeList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AnimeListState()
        )

    private fun getAnimeList() {
        viewModelScope.launch {
            getYourAnimeListUseCase().collect { animeInfo ->
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