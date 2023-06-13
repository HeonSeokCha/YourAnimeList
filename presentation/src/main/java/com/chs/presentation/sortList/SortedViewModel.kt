package com.chs.presentation.sortList

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
    private val getAnimeFilteredListUseCase: GetAnimeFilteredListUseCase,
    private val getSavedGenresUsaCase: GetSavedGenresUseCase,
    private val getRecentGenresUseCase: GetRecentGenresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SortState())
    val state = _state.asStateFlow()

    var selectMenuIdx: Int = 0

    init {
        initFilterList()
    }

    fun initSort(
        selectSort: UiConst.SortType? = null,
        selectSeason: UiConst.Season? = null,
        selectYear: Int = 0,
        selectGenre: String? = null
    ) {
        _state.update {
            it.copy(
                selectSort = if (selectSort != null) {
                    selectSort.name to selectSort.rawValue
                } else UiConst.SortType.TRENDING.name to UiConst.SortType.TRENDING.rawValue,
                selectSeason = if (selectSeason != null) {
                    selectSeason.name to selectSeason.rawValue
                } else null,
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

    fun changeFilterOptions(selectIdx: Int) {
        when (selectMenuIdx) {
            0 -> {
                _state.update {
                    it.copy(selectYear = it.menuList[0].second[selectIdx].second.toInt())
                }
            }

            1 -> {
                _state.update {
                    it.copy(selectSeason = it.menuList[1].second[selectIdx])
                }
            }

            2 -> {
                _state.update {
                    it.copy(selectSort = it.menuList[2].second[selectIdx])
                }
            }

            3 -> {
                _state.update {
                    it.copy(selectGenre = it.menuList[3].second[selectIdx].second)
                }
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