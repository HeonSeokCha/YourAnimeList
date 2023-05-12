package com.chs.presentation.animeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.GetSavedAnimeListUseCase
import com.chs.domain.usecase.GetSavedSearchAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getYourAnimeListUseCase: GetSavedAnimeListUseCase,
    private val getSavedSearchAnimeListUseCase: GetSavedSearchAnimeListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AnimeListState())
    val state = _state.asStateFlow()

    fun getYourAnimeList() {
        viewModelScope.launch {
            getYourAnimeListUseCase().collect { animeInfo ->
                _state.update {
                    it.copy(
                        animeList = animeInfo
                    )
                }
            }
        }
    }

    fun getSearchResultAnime() {
        viewModelScope.launch {
            getSavedSearchAnimeListUseCase("").collect { animeList ->
                _state.update {
                    it.copy(
                        animeList = animeList,
                        isLoading = false
                    )
                }
            }
        }
    }
}