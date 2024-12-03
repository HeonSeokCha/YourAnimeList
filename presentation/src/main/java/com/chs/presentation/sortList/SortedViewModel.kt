package com.chs.presentation.sortList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.chs.presentation.UiConst
import com.chs.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.domain.usecase.GetRecentGenresTagUseCase
import com.chs.domain.usecase.GetSaveTagUseCase
import com.chs.domain.usecase.GetSavedGenresUseCase
import com.chs.presentation.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SortedViewModel @Inject constructor(
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

    private val selectSort: UiConst.SortType = UiConst.SortType.entries.find {
        it.rawValue == savedStateHandle.toRoute<Screen.SortList>().sortOption
    } ?: UiConst.SortType.TRENDING

    private val selectSeason = UiConst.Season.entries.find {
        it.rawValue == savedStateHandle.toRoute<Screen.SortList>().season
    }

    private val selectYear: Int? = savedStateHandle.toRoute<Screen.SortList>().year
    private val selectGenre: String? = savedStateHandle.toRoute<Screen.SortList>().genre
    private val selectTags: String? = savedStateHandle.toRoute<Screen.SortList>().tag

    private fun getSortedAnime() {
        _state.update {
            it.copy(
                animeSortPaging = getAnimeFilteredListUseCase(
                    sortType = if (it.sortFilter.selectSort == UiConst.SortType.TRENDING.rawValue) {
                        listOf(UiConst.SortType.TRENDING.rawValue)
                    } else {
                        listOf(it.sortFilter.selectSort)
                    },
                    season = it.sortFilter.selectSeason,
                    year = it.sortFilter.selectYear,
                    genres = it.sortFilter.selectGenre,
                    tags = it.sortFilter.selectTags,
                    status = it.sortFilter.selectStatus
                ).cachedIn(viewModelScope)
            )
        }
    }

    fun changeSortEvent(event: SortEvent) {
        when (event) {
            is SortEvent.GetSortList -> {
                getSortedAnime()
                return
            }

            is SortEvent.ChangeSortOption -> {
                _state.update {
                    it.copy(
                        sortFilter = event.value
                    )
                }
            }
        }
        getSortedAnime()
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
                        selectSort = selectSort.rawValue,
                        selectSeason = selectSeason?.rawValue,
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