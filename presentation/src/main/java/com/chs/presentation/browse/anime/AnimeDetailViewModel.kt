package com.chs.presentation.browse.anime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.domain.model.AnimeInfo
import com.chs.domain.usecase.*
import com.chs.presentation.UiConst
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val checkSaveAnimeUseCase: GetSavedAnimeInfoUseCase,
    private val insertAnimeUseCase: InsertAnimeInfoUseCase,
    private val deleteAnimeUseCase: DeleteAnimeInfoUseCase,
    private val getAnimeThemeUseCase: GetAnimeThemeUseCase,
    private val getAnimeRecListUseCase: GetAnimeDetailRecListUseCase
) : ViewModel() {

    var state by mutableStateOf(AnimeDetailState())
        private set

    private val animeId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0
    private val animeMalId: Int = savedStateHandle[UiConst.TARGET_ID_MAL] ?: 0

    init {
        state = state.copy(
            animeId = animeId
        )

        changeEvent(AnimeDetailEvent.GetAnimeDetailInfo)
    }

    fun changeEvent(event: AnimeDetailEvent) {
        when (event) {
            is AnimeDetailEvent.InsertAnimeInfo -> {
                insertAnime(event.info)
            }

            is AnimeDetailEvent.DeleteAnimeInfo -> {
                deleteAnime(event.info)
            }

            is AnimeDetailEvent.GetAnimeDetailInfo -> {
                getAnimeDetailInfo(animeId)
                getAnimeTheme(animeMalId)
                getAnimeRecList(animeId)
                isSaveAnime(animeId)
            }
        }
    }

    private fun getAnimeDetailInfo(id: Int) {
        viewModelScope.launch {
            getAnimeDetailUseCase(id).collect { result ->
                state = state.copy(
                    animeDetailInfo = result
                )
            }
        }
    }

    private fun getAnimeTheme(idMal: Int) {
        viewModelScope.launch {
            getAnimeThemeUseCase(idMal).collect { result ->
                state = state.copy(
                    animeThemes = result
                )
            }
        }
    }

    private fun getAnimeRecList(id: Int) {
        state = state.copy(
            animeRecList = getAnimeRecListUseCase(id).cachedIn(viewModelScope)
        )
    }

    private fun isSaveAnime(animeId: Int) {
        viewModelScope.launch {
            checkSaveAnimeUseCase(animeId).collect { savedAnimeInfo ->
                state = state.copy(isSave = (savedAnimeInfo != null))
            }
        }
    }

    private fun insertAnime(anime: AnimeInfo?) {
        if (anime != null) {
            viewModelScope.launch {
                insertAnimeUseCase(anime)
            }
        }
    }

    private fun deleteAnime(anime: AnimeInfo?) {
        if (anime != null) {
            if (state.isSave) {
                viewModelScope.launch {
                    deleteAnimeUseCase(anime)
                }
            }
        }
    }
}