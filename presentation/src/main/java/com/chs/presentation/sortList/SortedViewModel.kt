package com.chs.presentation.sortList

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

    init {
        _state.update {
            it.copy(
                selectType = UiConst.SortType.POPULARITY.rawValue,
                selectSeason = UiConst.Season.WINTER.rawValue,
                selectYear = 2022
            )
        }
        getGenreList()
    }

    fun getSortedAnime() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    animeSortPaging = getAnimeFilteredListUseCase(
                        sortType = UiConst.SortType.TRENDING.rawValue,
                        season = it.selectSeason,
                        year = it.selectYear,
                        genre = it.selectGenre
                    ).cachedIn(viewModelScope)
                )
            }
        }
    }

    fun changeFilterOptions(
        selectSortType: UiConst.SortType? = null,
        selectSeason: UiConst.Season? = null,
        selectYear: Int? = null,
        selectGenre: String? = null
    ) {
        if (selectSortType != null) {
            _state.update {
                it.copy(selectType = selectSortType.rawValue)
            }
        }

        if (selectSeason != null) {
            _state.update {
                it.copy(selectSeason = selectSeason.rawValue)
            }
        }

        if (selectYear != null) {
            _state.update {
                it.copy(selectYear = selectYear)
            }
        }

        if (selectGenre != null) {
            _state.update {
                it.copy(selectGenre = selectGenre)
            }
        }
    }

    fun setFilterMenu(menuType: String) {
        when (menuType) {
            "Year" -> {
                _state.update {
                    it.copy(
                        menuList = UiConst.yearSortList.map { it.first }
                    )
                }
            }
            "Season" -> {
                _state.update {
                    it.copy(
                        menuList = UiConst.seasonFilterList.map { it.first }
                    )
                }
            }
            "Sort" -> {
                _state.update {
                    it.copy(
                        menuList = UiConst.sortTypeList.map { it.first }
                    )
                }
            }
            "Genre" -> {
//                _state.update {
//                    it.copy(
//                        menuList = it.genreList
//                    )
//                }
            }
        }
    }

    private fun getGenreList() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    genreList = getGenreListUseCase()
                )
            }
        }
    }
}