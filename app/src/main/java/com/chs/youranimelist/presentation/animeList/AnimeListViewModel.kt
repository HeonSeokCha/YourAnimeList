package com.chs.youranimelist.presentation.animeList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.domain.usecase.GetYourAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getYourAnimeListUseCase: GetYourAnimeListUseCase
) : ViewModel() {

    var state by mutableStateOf(AnimeListState())

    fun getYourAnimeList() {
        state = state.copy(isLoading = false)
        viewModelScope.launch {
            getYourAnimeListUseCase().collect {
                state = state.copy(
                    animeList = it,
                    isLoading = false
                )
            }
        }
    }
}