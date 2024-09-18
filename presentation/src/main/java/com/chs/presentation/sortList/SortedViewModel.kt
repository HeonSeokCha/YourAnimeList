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

    var state by mutableStateOf(SortState())
        private set

    init {
        initFilterList()

        val selectSort = UiConst.SortType.entries.find {
            it.rawValue == savedStateHandle[UiConst.KEY_SORT]
        } ?: UiConst.SortType.TRENDING

        val selectSeason = UiConst.Season.entries.find {
            it.rawValue == savedStateHandle[UiConst.KEY_SEASON]
        }

        val selectYear: Int = savedStateHandle[UiConst.KEY_YEAR] ?: 0
        val selectGenre: List<String>? = savedStateHandle[UiConst.KEY_GENRE]

        state = state.copy(
            selectSort = selectSort.name to selectSort.rawValue,
            selectSeason = if (selectSeason != null) selectSeason.name to selectSeason.rawValue else null,
            selectYear = if (selectYear != 0) selectYear else null,
            selectGenre = selectGenre
        )
        getSortedAnime()
    }

    private fun getSortedAnime() {
        state = state.copy(
            animeSortPaging = getAnimeFilteredListUseCase(
                sortType = if (state.selectSort!!.second == UiConst.SortType.TRENDING.rawValue) {
                    listOf(
                        UiConst.SortType.TRENDING.rawValue,
                        UiConst.SortType.POPULARITY.rawValue
                    )
                } else {
                    listOf(state.selectSort!!.second)
                },
                season = state.selectSeason?.second,
                year = state.selectYear,
                genres = state.selectGenre,
                tags = state.selectTags,
                status = state.selectStatus
            ).cachedIn(viewModelScope)
        )
    }

    fun changeSortEvent(event: SortEvent) {
        when (event) {

            is SortEvent.GetSortList -> {
                getSortedAnime()
            }

            is SortEvent.ChangeYearOption -> {
                state = state.copy(
                    selectYear = event.value
                )
            }

            is SortEvent.ChangeSeasonOption -> {
                state = state.copy(
                    selectSeason = event.value
                )
            }

            is SortEvent.ChangeSortOption -> {
                state = state.copy(
                    selectSort = event.value
                )
            }

            is SortEvent.ChangeGenreOption -> {
                state = state.copy(
                    selectGenre = event.value
                )
            }

            is SortEvent.ChangeStatusOption -> {
                state = state.copy(
                    selectStatus = event.value
                )
            }

            is SortEvent.ChangeTagOption -> {
                state = state.copy(
                    selectTags = event.value
                )
            }
        }
        getSortedAnime()
    }

    private fun initFilterList() {
        viewModelScope.launch {
            if (getSavedGenresUsaCase().isEmpty() || getSavedTagUseCase().isEmpty()) {
                getRecentGenresTagUseCase()
            }
            val genreList = getSavedGenresUsaCase()
            val tagList = getSavedTagUseCase()

            state = state.copy(
                optionGenres = genreList,
                optionTags = tagList
            )
        }
    }
}