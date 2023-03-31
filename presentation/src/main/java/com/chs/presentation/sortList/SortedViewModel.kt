package com.chs.presentation.sortList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.domain.usecase.GetGenreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortedViewModel @Inject constructor(
    private val getAnimeFilteredListUseCase: GetAnimeFilteredListUseCase,
    private val getGenreListUseCase: GetGenreListUseCase
) : ViewModel() {
    var selectedYear: Int? = null
//    var selectedSeason: MediaSeason? = null
//    var selectedSort: MediaSort? = MediaSort.TRENDING_DESC
    var selectGenre: String? = null
    var selectType = ""

    val filterList = mutableListOf(
        Pair("Year", "Any"),
        Pair("Season", "Any"),
        Pair("Sort", "Any"),
        Pair("Genre", "Any")
    )

    var state by mutableStateOf(SortState())
        private set

    init {
        getGenreList()
    }

    fun getSortedAnime() {
        state = state.copy(
            animeSortPaging = getAnimeFilteredListUseCase(
                selectType = selectType,
                sortType = selectedSort!!,
                season = selectedSeason,
                year = selectedYear,
                genre = selectGenre
            ).cachedIn(viewModelScope)
        )
    }

    private fun getGenreList() {
        viewModelScope.launch {
            state = state.copy(
                genreList = getGenreListUseCase()
            )
        }
    }
}