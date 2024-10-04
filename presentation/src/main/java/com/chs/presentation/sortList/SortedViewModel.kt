package com.chs.presentation.sortList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.presentation.UiConst
import com.chs.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.domain.usecase.GetRecentGenresTagUseCase
import com.chs.domain.usecase.GetSaveTagUseCase
import com.chs.domain.usecase.GetSavedGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    var state: SortState by mutableStateOf(SortState())
        private set


    init {
        initFilterList()

        val selectSort = UiConst.SortType.entries.find {
            it.rawValue == savedStateHandle[UiConst.KEY_SORT]
        } ?: UiConst.SortType.TRENDING

        val selectSeason = UiConst.Season.entries.find {
            it.rawValue == savedStateHandle[UiConst.KEY_SEASON]
        }

        val selectYear: Int? = savedStateHandle[UiConst.KEY_YEAR]
        val selectGenre: String? = savedStateHandle[UiConst.KEY_GENRE]
        val selectTags: String? = savedStateHandle[UiConst.KEY_TAG]

        state = state.copy(
            sortFilter = state.sortFilter.copy(
                selectSort = selectSort.rawValue,
                selectSeason = selectSeason?.rawValue,
                selectYear = selectYear,
                selectGenre = if (selectGenre == null) null else listOf(selectGenre),
                selectTags = if (selectTags == null) null else listOf(selectTags)
            ),
        )
        getSortedAnime()
    }

    private fun getSortedAnime() {
        state = state.copy(
            animeSortPaging = getAnimeFilteredListUseCase(
                sortType = if (state.sortFilter.selectSort == UiConst.SortType.TRENDING.rawValue) {
                    listOf(UiConst.SortType.TRENDING.rawValue)
                } else {
                    listOf(state.sortFilter.selectSort)
                },
                season = state.sortFilter.selectSeason,
                year = state.sortFilter.selectYear,
                genres = state.sortFilter.selectGenre,
                tags = state.sortFilter.selectTags,
                status = state.sortFilter.selectStatus
            ).cachedIn(viewModelScope)
        )
    }

    fun changeSortEvent(event: SortEvent) {
        when (event) {
            is SortEvent.GetSortList -> {
                getSortedAnime()
            }

            is SortEvent.ChangeSortOption -> {
                state = state.copy(
                    sortFilter = event.value
                )
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

            state = state.copy(
                optionGenres = getSavedGenresUsaCase(),
                optionTags = getSavedTagUseCase()
            )
        }
    }
}