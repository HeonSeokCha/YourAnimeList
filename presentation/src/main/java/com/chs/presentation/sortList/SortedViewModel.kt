package com.chs.presentation.sortList

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.domain.usecase.GetGenreListUseCase
import com.chs.presentation.Season
import com.chs.presentation.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortedViewModel @Inject constructor(
    private val getAnimeFilteredListUseCase: GetAnimeFilteredListUseCase,
    private val getGenreListUseCase: GetGenreListUseCase
) : ViewModel() {

    val filterList = mutableListOf(
        Pair("Year", "Any"),
        Pair("Season", "Any"),
        Pair("Sort", "Any"),
        Pair("Genre", "Any")
    )

    var state by mutableStateOf(SortState())
        private set

    init {
        state = state.copy(
            selectType = SortType.POPULARITY.rawValue,
            selectSeason = Season.WINTER.rawValue,
            selectYear = 2022
        )
        getGenreList()
        Log.e("State", state.toString())
    }

    fun getSortedAnime() {
        Log.e("State", state.toString())
        viewModelScope.launch {
            state = state.copy(
                animeSortPaging = getAnimeFilteredListUseCase(
                    sortType = SortType.TRENDING.rawValue,
                    season = state.selectSeason,
                    year = state.selectYear,
                    genre = state.selectGenre
                ).cachedIn(viewModelScope)
            )
        }
    }

    private fun getGenreList() {
//        viewModelScope.launch {
//            state = state.copy(
//                genreList = getGenreListUseCase()
//            )
//        }
    }
}