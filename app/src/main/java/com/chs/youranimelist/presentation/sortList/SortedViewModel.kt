package com.chs.youranimelist.presentation.sortList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.youranimelist.domain.usecase.GetGenreUseCase
import com.chs.youranimelist.domain.usecase.GetNoSeasonNoYearSortUseCase
import com.chs.youranimelist.domain.usecase.GetNoSeasonSortUseCase
import com.chs.youranimelist.domain.usecase.GetSeasonYearSortUseCase
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortedViewModel @Inject constructor(
    private val getNoSeasonNoYearSortUseCase: GetNoSeasonNoYearSortUseCase,
    private val getNoSeasonSortUseCase: GetNoSeasonSortUseCase,
    private val getSeasonYearSortUseCase: GetSeasonYearSortUseCase,
    private val getGenreUseCase: GetGenreUseCase
) : ViewModel() {
    var selectedYear: Int? = null
    var selectedSeason: MediaSeason? = null
    var selectedSort: MediaSort? = MediaSort.TRENDING_DESC
    var selectGenre: String? = null
    var page: Int = 1
    var selectType = ""
    var hasNextPage: Boolean = true

    val filterList = mutableListOf(
        Pair("Year", "Any"),
        Pair("Season", "Any"),
        Pair("Sort", "Any"),
        Pair("Genre", "Any"),
    )

    var state by mutableStateOf(SortState())

    fun getSortedAnime() {
        when (selectType) {
            Constant.SEASON_YEAR -> {
                state = state.copy(
                    animeSortPaging = getSeasonYearSortUseCase(
                        selectedSort!!,
                        selectedSeason!!,
                        selectedYear!!,
                        selectGenre
                    ).cachedIn(viewModelScope)
                )
            }

            Constant.NO_SEASON -> {
                state = state.copy(
                    animeNoSeasonSortPaging = getNoSeasonSortUseCase(
                        selectedSort!!,
                        selectedYear!!,
                        selectGenre
                    ).cachedIn(viewModelScope)
                )

            }

            Constant.NO_SEASON_NO_YEAR -> {
                state = state.copy(
                    animeNoSeasonNoYearSortPaging = getNoSeasonNoYearSortUseCase(
                        selectedSort!!,
                        selectGenre
                    ).cachedIn(viewModelScope)
                )
            }
        }
    }

    fun getGenreList() {
        viewModelScope.launch {
            getGenreUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            genreList = result.data?.genreCollection!!
                        )
                    }
                    is Resource.Loading -> { }
                    is Resource.Error -> { }
                }
            }
        }
    }
}