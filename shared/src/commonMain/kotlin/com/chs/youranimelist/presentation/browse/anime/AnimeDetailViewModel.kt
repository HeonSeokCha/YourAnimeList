package com.chs.youranimelist.presentation.browse.anime

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import app.cash.paging.cachedIn
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.util.onError
import com.chs.youranimelist.util.onSuccess
import com.chs.youranimelist.domain.usecase.*
import com.chs.youranimelist.presentation.browse.BrowseScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
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
            AnimeDetailState()
        )

    private val _animeDetailEvent: Channel<AnimeDetailEvent> = Channel()
    val animeDetailEvent = _animeDetailEvent.receiveAsFlow()

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

            is AnimeDetailEvent.OnTabSelected -> {
                _state.update {
                    it.copy(selectTabIdx = event.idx)
                }
            }

            else -> Unit
        }
    }

    private suspend fun initInfo() {
        getAnimeDetailInfo(animeId)
        getAnimeTheme(animeMalId)
        getAnimeRecList(animeId)
        isSaveAnime(animeId)
    }

    private suspend fun getAnimeDetailInfo(id: Int) {
        getAnimeDetailUseCase(id)
            .onSuccess { success ->
                _state.update {
                    it.copy(
                        animeDetailInfo = success
                    )
                }
            }.onError {
                _animeDetailEvent.send(AnimeDetailEvent.OnError)
            }
    }

    private suspend fun getAnimeTheme(idMal: Int) {
        if (idMal == 0) return

        getAnimeThemeUseCase(idMal)
            .onSuccess { success ->
                _state.update {
                    it.copy(animeThemes = success)
                }
            }.onError { error ->
                _animeDetailEvent.send(AnimeDetailEvent.OnError)
            }
    }

    private fun getAnimeRecList(id: Int) {
        _state.update {
            it.copy(animeRecList = getAnimeRecListUseCase(id).cachedIn(viewModelScope))
        }
    }

    private fun isSaveAnime(animeId: Int) {
        viewModelScope.launch {
            checkSaveAnimeUseCase(animeId).collect { savedAnimeInfo ->
                _state.update {
                    it.copy(isSave = savedAnimeInfo != null)
                }
            }
        }
    }

    private fun insertAnime(anime: AnimeInfo) {
        viewModelScope.launch {
            insertAnimeUseCase(anime)
        }
    }

    private fun deleteAnime(anime: AnimeInfo) {
        viewModelScope.launch {
            deleteAnimeUseCase(anime)
        }
    }
}