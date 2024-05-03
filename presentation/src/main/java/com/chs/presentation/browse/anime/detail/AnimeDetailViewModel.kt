package com.chs.presentation.browse.anime.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.common.Resource
import com.chs.domain.usecase.*
import com.chs.presentation.UiConst
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

    private val _state = MutableStateFlow(AnimeDetailState())
    val state = _state.asStateFlow()

    private val animeId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0
    private val animeMalId: Int = savedStateHandle[UiConst.TARGET_ID_MAL] ?: 0

    init {
        _state.update {
            it.copy(
                animeId = animeId
            )
        }

        getAnimeDetailInfo(animeId)
        getAnimeTheme(animeMalId)
        isSaveAnime(animeId)
    }

    private fun getAnimeDetailInfo(id: Int) {
        viewModelScope.launch {
            getAnimeDetailUseCase(id).collect { result ->
                _state.update {
                    when (result) {
                        is Resource.Loading -> {
                            it.copy(
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            it.copy(
                                animeDetailInfo = result.data,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getAnimeTheme(idMal: Int) {
        viewModelScope.launch {
            getAnimeThemeUseCase(idMal).collect { result ->
                _state.update {
                    when (result) {
                        is Resource.Loading -> {
                            it.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            it.copy(
                                animeThemes = result.data,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            Log.e("getAnimeDetailInfo", result.message.toString())
                            it.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun isSaveAnime(animeId: Int) {
        viewModelScope.launch {
            checkSaveAnimeUseCase(animeId).collect { savedAnimeInfo ->
                _state.update {
                    it.copy(isSave = (savedAnimeInfo != null))
                }
            }
        }
    }

    fun insertAnime() {
        val anime = state.value.animeDetailInfo?.animeInfo
        if (anime != null) {
            viewModelScope.launch {
                insertAnimeUseCase(anime)
            }
        }
    }

    fun deleteAnime() {
        val anime = state.value.animeDetailInfo?.animeInfo
        if (anime != null) {
            if (state.value.isSave) {
                viewModelScope.launch {
                    deleteAnimeUseCase(anime)
                }
            }
        }
    }
}