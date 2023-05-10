package com.chs.presentation.browse.anime.recommend

import androidx.lifecycle.ViewModel
import com.chs.domain.usecase.GetAnimeDetailRecListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AnimeRecViewModel @Inject constructor(
    private val getAnimeRecListUseCase: GetAnimeDetailRecListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AnimeRecState())
    val state = _state.asStateFlow()

    fun getAnimeRecommendList(animeId: Int) {
        _state.update {
            it.copy(
                animeRecInfo = getAnimeRecListUseCase.invoke(animeId)
            )
        }
    }
}