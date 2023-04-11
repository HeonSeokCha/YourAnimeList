package com.chs.presentation.sortList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.common.UiConst
import com.chs.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.domain.usecase.GetGenreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortedViewModel @Inject constructor(
    private val getAnimeFilteredListUseCase: GetAnimeFilteredListUseCase,
    private val getGenreListUseCase: GetGenreListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SortState())
    val state = _state.asStateFlow()

    var selectMenuIdx: Int = 0

    fun initSort(
        selectType: UiConst.SortType? = null,
        selectSeason: UiConst.Season? = null,
        selectYear: String? = null,
        selectGenre: String? = null
    ) {
        if (selectType != null) {
            Log.e("selectType", selectType.toString())
            _state.update {
                it.copy(selectType = selectType.rawValue)
            }
        }

        if (selectSeason != null) {
            Log.e("selectSeason", selectSeason.toString())
            _state.update {
                it.copy(selectSeason = selectSeason.rawValue)
            }
        }

        if (selectYear != null) {
            Log.e("selectYear", selectYear.toString())
            _state.update {
                it.copy(selectYear = 2023)
            }
        }

        if (selectGenre != null) {
            Log.e("selectGenre", (selectGenre == "null").toString())
            _state.update {
                it.copy(selectGenre = selectGenre)
            }
        }

        getSortedAnime()
    }

    private fun getSortedAnime() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    animeSortPaging = getAnimeFilteredListUseCase(
                        sortType = it.selectType ?: UiConst.SortType.TRENDING.rawValue,
                        season = it.selectSeason,
                        year = it.selectYear,
                        genre = it.selectGenre
                    ).cachedIn(viewModelScope)
                )
            }
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
                    it.copy(selectSeason = it.menuList[1].second[selectIdx].second)
                }
            }
            2 -> {
                _state.update {
                    it.copy(selectType = it.menuList[2].second[selectIdx].second)
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
            1 -> state.value.selectSeason ?: "Any"
            2 -> state.value.selectType ?: "Any"
            3 -> state.value.selectGenre ?: "Any"
            else -> "Any"
        }
    }

    fun getGenreList() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    menuList = listOf(
                        "Year" to UiConst.yearSortList,
                        "Season" to UiConst.seasonFilterList,
                        "Sort" to UiConst.sortTypeList,
                        "Genre" to getGenreListUseCase().map { genre -> genre to genre }
                    )
                )
            }
        }
    }
}