package com.chs.presentation.browse.anime.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.common.Resource
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.usecase.*
import com.chs.presentation.UiConst
import com.chs.presentation.browse.MediaDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
) : ViewModel() {

    var state by mutableStateOf(AnimeDetailState())
        private set

    private val animeId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0
    private val animeMalId: Int = savedStateHandle[UiConst.TARGET_ID_MAL] ?: 0

    init {
        state = state.copy(
            animeId = animeId
        )

        getAnimeDetailInfo(animeId)
        getAnimeTheme(animeMalId)
        isSaveAnime(animeId)
    }

    fun changeEvent(mediaDetailEvent: MediaDetailEvent) {
        when (mediaDetailEvent) {
            is MediaDetailEvent.InsertMediaInfo -> {
                insertAnime()
            }

            is MediaDetailEvent.DeleteMediaInfo -> {
                deleteAnime()
            }
        }
    }

    private fun getAnimeDetailInfo(id: Int) {
        viewModelScope.launch {
            getAnimeDetailUseCase(id).collect { result ->
                state = when (result) {
                    is Resource.Loading -> {
                        state.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        state.copy(
                            animeDetailInfo = result.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        state.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun getAnimeTheme(idMal: Int) {
        viewModelScope.launch {
            getAnimeThemeUseCase(idMal).collect { result ->
                state = when (result) {
                    is Resource.Loading -> {
                        state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state.copy(
                            animeThemes = result.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        Log.e("getAnimeDetailInfo", result.message.toString())
                        state.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun isSaveAnime(animeId: Int) {
        viewModelScope.launch {
            checkSaveAnimeUseCase(animeId).collect { savedAnimeInfo ->
                state = state.copy(isSave = (savedAnimeInfo != null))
            }
        }
    }

    private fun insertAnime() {
        val anime = state.animeDetailInfo?.animeInfo
        if (anime != null) {
            viewModelScope.launch {
                insertAnimeUseCase(anime)
            }
        }
    }

    private fun deleteAnime() {
        val anime = state.animeDetailInfo?.animeInfo
        if (anime != null) {
            if (state.isSave) {
                viewModelScope.launch {
                    deleteAnimeUseCase(anime)
                }
            }
        }
    }
}