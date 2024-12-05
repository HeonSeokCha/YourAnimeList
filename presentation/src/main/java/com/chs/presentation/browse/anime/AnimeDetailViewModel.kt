package com.chs.presentation.browse.anime

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.chs.domain.model.AnimeInfo
import com.chs.domain.usecase.*
import com.chs.presentation.browse.BrowseScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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
    private val getAnimeRecListUseCase: GetAnimeDetailRecListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AnimeDetailState())
    val state = _state
        .onStart {
            initInfo()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val animeId: Int = savedStateHandle.toRoute<BrowseScreen.AnimeDetail>().id
    private val animeMalId: Int = savedStateHandle.toRoute<BrowseScreen.AnimeDetail>().idMal

    fun changeEvent(event: AnimeDetailEvent) {
        when (event) {
            is AnimeDetailEvent.InsertAnimeInfo -> {
                insertAnime(event.info)
            }

            is AnimeDetailEvent.DeleteAnimeInfo -> {
                deleteAnime(event.info)
            }

            is AnimeDetailEvent.GetAnimeDetailInfo -> {
                initInfo()
            }

            else -> Unit
        }
    }

    private fun initInfo() {
        getAnimeDetailInfo(animeId)
        getAnimeTheme(animeMalId)
        getAnimeRecList(animeId)
        isSaveAnime(animeId)
    }

    private fun getAnimeDetailInfo(id: Int) {
        viewModelScope.launch {
            getAnimeDetailUseCase(id).collect { result ->
                _state.update {
                    it.copy(
                        animeDetailInfo = result
                    )
                }
            }
        }
    }

    private fun getAnimeTheme(idMal: Int) {
        viewModelScope.launch {
            getAnimeThemeUseCase(idMal).collect { result ->
                _state.update {
                    it.copy(
                        animeThemes = result
                    )
                }
            }
        }
    }

    private fun getAnimeRecList(id: Int) {
        _state.update {
            it.copy(
                animeRecList = getAnimeRecListUseCase(id).cachedIn(viewModelScope)
            )
        }
    }

    private fun isSaveAnime(animeId: Int) {
        checkSaveAnimeUseCase(animeId).onEach { savedAnimeInfo ->
            _state.update {
                it.copy(
                    isSave = savedAnimeInfo != null
                )
            }
        }.launchIn(viewModelScope)
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
            if (state.value.isSave) {
                viewModelScope.launch {
                    deleteAnimeUseCase(anime)
                }
            }
        }
    }
}