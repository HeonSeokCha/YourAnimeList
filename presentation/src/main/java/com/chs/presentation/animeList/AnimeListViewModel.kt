package com.chs.presentation.animeList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.GetAnimeSearchResultUseCase
import com.chs.domain.usecase.GetSavedAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getYourAnimeListUseCase: GetSavedAnimeListUseCase,
    private val searchAnimeListUseCase: GetAnimeSearchResultUseCase
) : ViewModel() {

    var state by mutableStateOf(AnimeListState())

    fun getYourAnimeList() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            getYourAnimeListUseCase().collect {
                state = state.copy(
                    animeList = it,
                    isLoading = false
                )
            }
        }
    }

    fun getSearchResultAnime(query: String) {
        viewModelScope.launch {
            searchAnimeListUseCase(query).collect {
                state = state.copy(
                    animeList = it,
                    isLoading = false
                )
            }
        }
    }
}