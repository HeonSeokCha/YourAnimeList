package com.chs.youranimelist.presentation.sortList

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var page: Int = 0
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
        Log.e("Sorted", page.toString())
        viewModelScope.launch {
            when (selectType) {
                Constant.SEASON_YEAR -> {
                    getSeasonYearSortUseCase(
                        page,
                        selectedSort!!,
                        selectedSeason!!,
                        selectedYear!!,
                        selectGenre
                    ).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                hasNextPage = result.data?.pageInfo?.hasNextPage!!
                                result.data.media?.forEach { anime ->
                                    state.animeSortList.add(anime!!.animeList)
                                }
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                            is Resource.Error -> {
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                            is Resource.Loading -> {
                                state = state.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
                }

                Constant.NO_SEASON -> {
                    getNoSeasonSortUseCase(
                        page,
                        selectedSort!!,
                        selectedYear!!,
                        selectGenre
                    ).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                hasNextPage = result.data?.pageInfo?.hasNextPage!!
                                result.data.media?.forEach { anime ->
                                    state.animeSortList.add(anime!!.animeList)
                                }
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                            is Resource.Error -> {
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                            is Resource.Loading -> {
                                state = state.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
                }

                Constant.NO_SEASON_NO_YEAR -> {
                    getNoSeasonNoYearSortUseCase(
                        page,
                        selectedSort!!,
                        selectGenre
                    ).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                hasNextPage = result.data?.pageInfo?.hasNextPage!!
                                result.data.media?.forEach { anime ->
                                    state.animeSortList.add(anime!!.animeList)
                                }
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                            is Resource.Error -> {
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                            is Resource.Loading -> {
                                state = state.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
                }
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

    fun refresh() {
        page = 1
        hasNextPage = true
        state = state.copy(animeSortList = arrayListOf())
        getSortedAnime()
    }
}