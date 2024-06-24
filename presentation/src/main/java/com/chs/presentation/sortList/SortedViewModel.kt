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
import com.chs.domain.usecase.GetRecentGenresUseCase
import com.chs.domain.usecase.GetSavedGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SortedViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAnimeFilteredListUseCase: GetAnimeFilteredListUseCase,
    private val getSavedGenresUsaCase: GetSavedGenresUseCase,
    private val getRecentGenresUseCase: GetRecentGenresUseCase
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
        val selectGenre: String? = savedStateHandle[UiConst.KEY_GENRE]

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
                genre = state.selectGenre
            ).cachedIn(viewModelScope)
        )
    }

    fun getSelectedOption(selectIdx: Int): String {
        return when (selectIdx) {
            0 -> "${state.selectYear ?: "Any"}"
            1 -> state.selectSeason?.first ?: "Any"
            2 -> state.selectSort?.first ?: "Any"
            3 -> state.selectGenre ?: "Any"
            else -> "Any"
        }
    }

    fun changeSortEvent(event: SortEvent) {
        when (event) {

            is SortEvent.GetSortList -> {

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
        }
        getSortedAnime()
    }

    private fun initFilterList() {
        viewModelScope.launch {
            val genreList = withContext(Dispatchers.IO) {
                if (getSavedGenresUsaCase().isEmpty()) {
                    getRecentGenresUseCase()
                }
                getSavedGenresUsaCase()
            }
            state = state.copy(
                optionGenres = genreList.map { it to it }
            )
        }
    }
}