package com.chs.youranimelist.presentation.browse.anime

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.SeasonType
import com.chs.youranimelist.util.onError
import com.chs.youranimelist.util.onSuccess
import com.chs.youranimelist.domain.usecase.*
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.browse.anime.AnimeDetailEffect.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
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
    getAnimeRecListUseCase: GetAnimeDetailRecListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AnimeDetailState())
    val state = _state
        .onStart { initInfo() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _effect: Channel<AnimeDetailEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val animeId: Int = savedStateHandle.toRoute<BrowseScreen.AnimeDetail>().id
    private val animeMalId: Int = savedStateHandle.toRoute<BrowseScreen.AnimeDetail>().idMal
    val animeRecPaging: Flow<PagingData<AnimeInfo>> =
        getAnimeRecListUseCase(animeId).cachedIn(viewModelScope)

    fun handleIntent(intent: AnimeDetailIntent) {
        when (intent) {
            is AnimeDetailIntent.ClickChara -> {
                _effect.trySend(NavigateCharaDetail(id = intent.id))
            }

            is AnimeDetailIntent.ClickGenre -> {
                _effect.trySend(NavigateSortGenre(genre = intent.genre))
            }

            is AnimeDetailIntent.ClickLink -> {
                _effect.trySend(NavigateBrowser(url = intent.url))
            }

            is AnimeDetailIntent.ClickSeasonYear -> {
                _effect.trySend(
                    NavigateSortSeasonYear(
                        seasonType = intent.season,
                        year = intent.year
                    )
                )
            }

            is AnimeDetailIntent.ClickStudio -> {
                _effect.trySend(NavigateStudio(id = intent.id))
            }

            is AnimeDetailIntent.ClickTag -> {
                _effect.trySend(NavigateSortTag(tag = intent.tag))
            }

            is AnimeDetailIntent.ClickTrailer -> {
                _effect.trySend(NavigateYouTube(id = intent.id))
            }

            is AnimeDetailIntent.ClickAnime -> {
                _effect.trySend(
                    NavigateAnimeDetail(
                        id = intent.id,
                        idMal = intent.idMal
                    )
                )
            }

            AnimeDetailIntent.ClickClose -> _effect.trySend(NavigateClose)

            is AnimeDetailIntent.ClickSaved -> handleSavedAnime(intent.info)

            is AnimeDetailIntent.OnTabSelected -> _state.update { it.copy(selectTabIdx = intent.idx) }
            AnimeDetailIntent.OnAppendLoadCompleteRecList -> _state.update {
                it.copy(
                    animeRecListAppendLoading = false
                )
            }

            AnimeDetailIntent.OnAppendLoadRecList -> _state.update {
                it.copy(
                    animeRecListAppendLoading = true
                )
            }

            AnimeDetailIntent.OnErrorRecList -> _state.update { it.copy(animeRecListError = true) }
            AnimeDetailIntent.OnLoadCompleteRecList -> _state.update { it.copy(animeRecListLoading = false) }
            AnimeDetailIntent.OnLoadRecList -> _state.update {
                it.copy(
                    animeRecListLoading = true,
                    animeRecListError = false
                )
            }

            AnimeDetailIntent.ClickDialogConfirm -> _state.update { it.copy(isShowDialog = false) }
            AnimeDetailIntent.ClickExpand -> _state.update { it.copy(isDescExpand = !it.isDescExpand) }
            is AnimeDetailIntent.ClickSpoiler -> {
                _state.update {
                    it.copy(
                        isShowDialog = true,
                        dialogText = intent.desc
                    )
                }
            }
            is AnimeDetailIntent.LongClickTag -> {
                _state.update {
                    it.copy(
                        isShowDialog = true,
                        dialogText = intent.tag
                    )
                }
            }
        }
    }

    private suspend fun initInfo() {
        getAnimeDetailInfo(animeId)
        getAnimeTheme(animeMalId)
        isSaveAnime(animeId)
    }

    private suspend fun getAnimeDetailInfo(id: Int) {
        getAnimeDetailUseCase(id)
            .onSuccess { info ->
                _state.update {
                    it.copy(animeDetailInfo = info)
                }
            }.onError {
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

    private fun handleSavedAnime(anime: AnimeInfo) {
        viewModelScope.launch {
            if (_state.value.isSave) {
                deleteAnimeUseCase(anime)
            } else {
                insertAnimeUseCase(anime)
            }
        }
    }
}