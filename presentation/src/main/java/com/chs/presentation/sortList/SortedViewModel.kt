package com.chs.presentation.sortList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.presentation.type.MediaSeason
import com.chs.presentation.type.MediaSort
import com.chs.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortedViewModel @Inject constructor(
    private val getSortListUseCase: GetSortListUseCase,
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
        state = state.copy(
            animeSortPaging = getSortListUseCase(
                selectType = selectType,
                sort = selectedSort!!,
                season = selectedSeason,
                seasonYear = selectedYear,
                genre = selectGenre
            ).cachedIn(viewModelScope)
        )
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