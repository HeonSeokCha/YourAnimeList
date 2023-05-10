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

    var selectMenuIdx: Int = 0

    init {
        getGenreList()
    }

    fun initSort(
        selectType: UiConst.SortType? = null,
        selectSeason: UiConst.Season? = null,
        selectYear: Int = 0,
        selectGenre: String? = null
    ) {

        _state.update {
            it.copy(
                selectType = if (selectType != null) {
                    selectType.name to selectType.rawValue
                } else null,
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
                    sortType = it.selectType?.second ?: UiConst.SortType.TRENDING.rawValue,
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
                    it.copy(selectType = it.menuList[2].second[selectIdx])
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
            2 -> state.value.selectType?.first ?: "Any"
            3 -> state.value.selectGenre ?: "Any"
            else -> "Any"
        }
    }

    private fun getGenreList() {
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