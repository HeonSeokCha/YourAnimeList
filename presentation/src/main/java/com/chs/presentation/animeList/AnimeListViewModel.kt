package com.chs.presentation.animeList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var state: AnimeListState by mutableStateOf(AnimeListState())
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