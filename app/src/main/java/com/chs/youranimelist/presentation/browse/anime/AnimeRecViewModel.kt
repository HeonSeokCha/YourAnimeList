package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.domain.usecase.GetAnimeRecUseCase
import com.chs.youranimelist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeRecViewModel @Inject constructor(
    private val getAnimeRecUseCase: GetAnimeRecUseCase
) : ViewModel() {
    var state by mutableStateOf(AnimeRecState())

    fun getAnimeRecommendList(animeId: Int) {
        viewModelScope.launch {
            getAnimeRecUseCase(animeId, 1).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            animeRecInfo = result.data
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
}