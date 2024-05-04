package com.chs.presentation.animeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.GetSavedAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getYourAnimeListUseCase: GetSavedAnimeListUseCase
) : ViewModel() {

    var state: AnimeListState = AnimeListState()
        private set

    init {
        viewModelScope.launch {
            getYourAnimeListUseCase().collect { animeInfo ->
                state = state.copy(
                    animeList = animeInfo
                )
            }
        }
    }
}