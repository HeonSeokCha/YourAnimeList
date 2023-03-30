package com.chs.presentation.browse.anime.recommend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.GetAnimeDetailRecListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeRecViewModel @Inject constructor(
    private val getAnimeRecListUseCase: GetAnimeDetailRecListUseCase
) : ViewModel() {

    var state by mutableStateOf(AnimeRecState())

    fun getAnimeRecommendList(animeId: Int) {
        viewModelScope.launch {
            state = state.copy(
                animeRecInfo = getAnimeRecListUseCase.invoke(animeId)
            )
        }
    }
}