package com.chs.presentation.browse.anime

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.common.Resource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
            getAnimeDetailUseCase(id).collect {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            animeDetailInfo = it.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        Log.e("getAnimeDetailInfo", it.message.toString())
                    }
                }
            }
        }
    }

    fun getAnimeTheme(idMal: Int) {
        viewModelScope.launch {
            getAnimeThemeUseCase(idMal).collect {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            animeThemes = it.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        Log.e("getAnimeDetailInfo", it.message.toString())
                    }
                }
            }
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