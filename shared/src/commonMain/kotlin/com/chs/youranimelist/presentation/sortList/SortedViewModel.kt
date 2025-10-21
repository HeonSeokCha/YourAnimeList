package com.chs.youranimelist.presentation.sortList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.SeasonType
import com.chs.youranimelist.domain.model.SortFilter
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.youranimelist.domain.usecase.GetRecentGenresTagUseCase
import com.chs.youranimelist.domain.usecase.GetSaveTagUseCase
import com.chs.youranimelist.domain.usecase.GetSavedGenresUseCase
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.main.Screen
import com.chs.youranimelist.presentation.sortList.SortEffect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SortedViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getAnimeFilteredListUseCase: GetAnimeFilteredListUseCase,
    private val getSavedGenresUsaCase: GetSavedGenresUseCase,
    private val getSavedTagUseCase: GetSaveTagUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(SortState())
    val state = _state
        .onStart { initFilterList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val sortFilter = MutableStateFlow(SortFilter())

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingItem = sortFilter
        .flatMapLatest { getAnimeFilteredListUseCase(it) }
        .cachedIn(viewModelScope)

    private val _effect: Channel<SortEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    fun handleIntent(intent: SortIntent) {
        when (intent) {
            is SortIntent.ClickAnime -> _effect.trySend(
                NavigateAnimeDetail(
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
                sortFilter.update { _state.value.sortFilter }
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

            SortIntent.AppendLoadComplete -> _state.update { it.copy(isAppending = false) }
            SortIntent.LoadComplete -> _state.update { it.copy(isLoading = false) }
            SortIntent.OnAppendLoad -> _state.update { it.copy(isAppending = true) }
            SortIntent.OnLoad -> _state.update { it.copy(isLoading = true) }
        }
    }

    private fun initFilterList() {
        val selectSort: List<SortType> =
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

        val selectSeason = SeasonType.entries.find {
            it.rawValue == savedStateHandle.toRoute<Screen.SortList>().season
        }

        val selectYear: Int? = savedStateHandle.toRoute<Screen.SortList>().year
        val selectGenre: String? = savedStateHandle.toRoute<Screen.SortList>().genre
        val selectTags: String? = savedStateHandle.toRoute<Screen.SortList>().tag

        viewModelScope.launch {
            val filter = SortFilter(
                selectSort = selectSort,
                selectSeason = selectSeason,
                selectYear = selectYear,
                selectGenre = if (selectGenre == null) null else listOf(selectGenre),
                selectTags = if (selectTags == null) null else listOf(selectTags)
            )

            _state.update {
                it.copy(
                    sortFilter = filter,
                    sortOptions = it.sortOptions.copy(
                        optionGenres = getSavedGenresUsaCase(),
                        optionTags = getSavedTagUseCase()
                    )
                )
            }

            sortFilter.update { filter }
        }
    }
}