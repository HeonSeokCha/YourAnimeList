package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeCharaViewModel @Inject constructor(
    private val getAnimeCharaUseCase: GetAnimeCharaUseCase
) : ViewModel() {
    var state by mutableStateOf(AnimeCharaState())

    fun getAnimeCharacters(animeId: Int) {
        viewModelScope.launch {
            getAnimeCharaUseCase(animeId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            animeCharaInfo = result.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = result.message.toString()
                        )
                    }
                }
            }
        }
    }
}