package com.chs.youranimelist.presentation.sortList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.SeasonType
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.youranimelist.domain.usecase.GetRecentGenresTagUseCase
import com.chs.youranimelist.domain.usecase.GetSaveTagUseCase
import com.chs.youranimelist.domain.usecase.GetSavedGenresUseCase
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.main.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SortedViewModel(
    savedStateHandle: SavedStateHandle,
    private val getAnimeFilteredListUseCase: GetAnimeFilteredListUseCase,
    private val getSavedGenresUsaCase: GetSavedGenresUseCase,
    private val getSavedTagUseCase: GetSaveTagUseCase,
    private val getRecentGenresTagUseCase: GetRecentGenresTagUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(SortState())
    val state = _state
        .onStart {
            initFilterList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _effect: Channel<SortEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val selectSort: List<SortType> =
        savedStateHandle.toRoute<Screen.SortList>().sortOption
            .run {
                if (this.isEmpty()) {
                    listOf(
                        SortType.POPULARITY,
                        SortType.AVERAGE,
                    )
                } else {
                    this.map { rawValue ->
                        SortType.entries.find {
                            it.rawValue == rawValue
                        } ?: SortType.TRENDING
                    }
                }
            }

    private val selectSeason = SeasonType.entries.find {
        it.rawValue == savedStateHandle.toRoute<Screen.SortList>().season
    }

    private val selectYear: Int? = savedStateHandle.toRoute<Screen.SortList>().year
    private val selectGenre: String? = savedStateHandle.toRoute<Screen.SortList>().genre
    private val selectTags: String? = savedStateHandle.toRoute<Screen.SortList>().tag

    private fun getSortedAnime() {
        _state.update {
            it.copy(
                isRefresh = false,
                isLoading = false,
                animeSortPaging = getAnimeFilteredListUseCase(it.sortFilter)
                    .cachedIn(viewModelScope)
            )
        }
    }

    fun handleIntent(intent: SortIntent) {
        when (intent) {
            is SortIntent.ClickAnime -> _effect.trySend(
                SortEffect.NavigateAnimeDetail(
                    id = intent.id,
                    idMal = intent.idMal
                )
            )

            SortIntent.DismissFilterDialog -> {
                _state.update { it.copy(isShowDialog = false) }
            }

            SortIntent.ClickFilterDialog -> {
                _state.update { it.copy(isShowDialog = true) }
            }

            SortIntent.ClickFilterApply -> {
                _state.update { it.copy(isShowDialog = false) }
                getSortedAnime()
            }

            is SortIntent.OnChangeGenres -> {
                _state.update {
                    it.copy(
                        sortFilter = it.sortFilter.copy(
                            selectGenre = intent.genres
                        )
                    )
                }
            }

            is SortIntent.OnChangeSeason -> {
                _state.update {
                    it.copy(
                        sortFilter = it.sortFilter.copy(
                            selectSeason = intent.season
                        )
                    )
                }
            }

            is SortIntent.OnChangeSort -> {
                _state.update {
                    it.copy(
                        sortFilter = it.sortFilter.copy(
                            selectSort = listOf(intent.sort)
                        )
                    )
                }
            }

            is SortIntent.OnChangeStatus -> {
                _state.update {
                    it.copy(
                        sortFilter = it.sortFilter.copy(
                            selectStatus = intent.status
                        )
                    )
                }
            }

            is SortIntent.OnChangeTags -> {
                _state.update {
                    it.copy(
                        sortFilter = it.sortFilter.copy(
                            selectTags = intent.tags
                        )
                    )
                }
            }

            is SortIntent.OnChangeYear -> {
                _state.update {
                    it.copy(
                        sortFilter = it.sortFilter.copy(
                            selectYear = intent.year
                        )
                    )
                }
            }
        }
    }

    private fun initFilterList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (getSavedGenresUsaCase().isEmpty() || getSavedTagUseCase().isEmpty()) {
                    getRecentGenresTagUseCase()
                }
            }

            _state.update {
                it.copy(
                    sortFilter = it.sortFilter.copy(
                        selectSort = selectSort,
                        selectSeason = selectSeason,
                        selectYear = selectYear,
                        selectGenre = if (selectGenre == null) null else listOf(selectGenre),
                        selectTags = if (selectTags == null) null else listOf(selectTags)
                    ),
                    sortOptions = it.sortOptions.copy(
                        optionGenres = getSavedGenresUsaCase(),
                        optionTags = getSavedTagUseCase()
                    )
                )
            }

            getSortedAnime()
        }
    }
}