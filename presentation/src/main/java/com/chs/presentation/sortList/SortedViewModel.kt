package com.chs.presentation.sortList

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

    private val _state = MutableStateFlow(SortState())
    val state = _state.asStateFlow()

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

        _state.update {
            it.copy(
                selectSort = selectSort.name to selectSort.rawValue,
                selectSeason = if (selectSeason != null) selectSeason.name to selectSeason.rawValue else null,
                selectYear = if (selectYear != 0) selectYear else null,
                selectGenre = selectGenre
            )
        }

        getSortedAnime()
    }

    private fun getSortedAnime() {
        _state.update {
            it.copy(
                animeSortPaging = getAnimeFilteredListUseCase(
                    sortType = if (it.selectSort!!.second == UiConst.SortType.TRENDING.rawValue) {
                        listOf(
                            UiConst.SortType.TRENDING.rawValue,
                            UiConst.SortType.POPULARITY.rawValue
                        )
                    } else {
                        listOf(it.selectSort.second)
                    },
                    season = it.selectSeason?.second,
                    year = it.selectYear,
                    genre = it.selectGenre
                ).cachedIn(viewModelScope)
            )
        }
    }

    fun changeFiletMenuIdx(idx: Int) {
        _state.update {
            it.copy(
                selectMenuIdx = idx
            )
        }
    }

    fun changeFilterOptions(selectIdx: Int) {
        _state.update {
            if (it.selectMenuIdx == 0) {
                it.copy(selectYear = it.menuList[0].second[selectIdx].second.toInt())
            } else {
                it.copy(selectSeason = it.menuList[it.selectMenuIdx].second[selectIdx])
            }
        }
        getSortedAnime()
    }

    fun getSelectedOption(selectIdx: Int): String {
        return when (selectIdx) {
            0 -> "${state.value.selectYear ?: "Any"}"
            1 -> state.value.selectSeason?.first ?: "Any"
            2 -> state.value.selectSort?.first ?: "Any"
            3 -> state.value.selectGenre ?: "Any"
            else -> "Any"
        }
    }

    private fun initFilterList() {
        viewModelScope.launch {
            val genreList = withContext(Dispatchers.IO) {
                if (getSavedGenresUsaCase().isEmpty()) {
                    getRecentGenresUseCase()
                }
                getSavedGenresUsaCase()
            }
            _state.update {
                it.copy(
                    menuList = listOf(
                        "Year" to UiConst.yearSortList,
                        "Season" to UiConst.seasonFilterList.map { it.name to it.rawValue },
                        "Sort" to UiConst.sortTypeList.map { it.name to it.rawValue },
                        "Genre" to genreList.map { it to it }
                    )
                )
            }
        }
    }
}