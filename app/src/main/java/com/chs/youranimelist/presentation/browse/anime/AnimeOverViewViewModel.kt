package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.domain.usecase.GetAnimeOverViewUseCase
import com.chs.youranimelist.domain.usecase.GetAnimeThemeUseCase
import com.chs.youranimelist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeOverViewViewModel @Inject constructor(
    private val getAnimeOverViewUseCase: GetAnimeOverViewUseCase,
    private val getAnimeThemeUseCase: GetAnimeThemeUseCase
) : ViewModel() {

    var state by mutableStateOf(AnimeOverViewState())

    fun getAnimeOverView(animeId: Int) {
        viewModelScope.launch {
            getAnimeOverViewUseCase(animeId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            animeOverViewInfo = result.data,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getAnimeTheme(animeMalId: Int) {
        viewModelScope.launch {
            getAnimeThemeUseCase(animeMalId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(animeOverThemeInfo = result.data)
                    }

                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = false)
                    }
                }
            }
        }
    }

}