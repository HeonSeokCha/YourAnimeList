package com.chs.presentation.browse.anime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.model.AnimeInfo
import com.chs.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val checkSaveAnimeUseCase: GetSavedAnimeInfoUseCase,
    private val insertAnimeUseCase: InsertAnimeInfoUseCase,
    private val deleteAnimeUseCase: DeleteAnimeInfoUseCase,
    private val getAnimeThemeUseCase: GetAnimeThemeUseCase,
) : ViewModel() {

    var state by mutableStateOf(AnimeDetailState())
        private set

    fun getAnimeDetailInfo(id: Int) {
        viewModelScope.launch {
            state = state.copy(
                animeDetailInfo = getAnimeDetailUseCase(id),
                isLoading = false
            )
        }
    }

    fun getAnimeTheme(idMal: Int) {
        viewModelScope.launch {
            state = state.copy(
                animeThemes = getAnimeThemeUseCase(idMal),
                isLoading = false
            )
        }
    }

    fun isSaveAnime(animeId: Int) {
        viewModelScope.launch {
            checkSaveAnimeUseCase(animeId).collect {
                state = if (it != null && it.id == animeId) {
                    state.copy(isSaveAnime = it)
                } else {
                    state.copy(isSaveAnime = null)
                }
            }
        }
    }

    fun insertAnime() {
        val anime = state.animeDetailInfo?.animeInfo!!
        viewModelScope.launch {
            insertAnimeUseCase(anime)
        }
    }

    fun deleteAnime(anime: AnimeInfo) {
        viewModelScope.launch {
            deleteAnimeUseCase(anime)
        }
    }
}