package com.chs.youranimelist.presentation.sortedlist

import androidx.lifecycle.*
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.usecase.GetGenreUseCase
import com.chs.youranimelist.domain.usecase.GetNoSeasonNoYearUseCase
import com.chs.youranimelist.domain.usecase.GetNoSeasonUseCase
import com.chs.youranimelist.domain.usecase.GetSeasonYearUseCase
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.GenreQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortedListViewModel @Inject constructor(
    private val seasonYearUseCase: GetSeasonYearUseCase,
    private val noSeasonUseCase: GetNoSeasonUseCase,
    private val noSeasonNoYearUseCase: GetNoSeasonNoYearUseCase,
    private val genreUseCase: GetGenreUseCase
) : ViewModel() {

    private val _animeListResponse = SingleLiveEvent<NetworkState<AnimeListQuery.Page>>()

    private val _noSeasonNoYearListResponse =
        SingleLiveEvent<NetworkState<NoSeasonNoYearQuery.Page>>()

    private val _noSeasonListResponse =
        SingleLiveEvent<NetworkState<NoSeasonQuery.Page>>()

    private val _genreListResponse = SingleLiveEvent<NetworkState<GenreQuery.Data>>()
    val genreListResponse: LiveData<NetworkState<GenreQuery.Data>>
        get() = _genreListResponse

    var selectedYear: Int? = null
    var selectedSeason: MediaSeason? = null
    var selectedSort: MediaSort? = null
    var selectGenre: String? = null

    var page: Int = 1
    var selectType = ""
    var hasNextPage: Boolean = true
    var isSeason: Boolean = false
    var animeResultList: ArrayList<AnimeList?> = ArrayList()
    var genreList: ArrayList<String> = ArrayList()
    var testList: MutableList<String> = mutableListOf()

    fun initFilterList(
        year: String = "",
        season: String = "",
        sort: String = "",
        genre: String = ""
    ) {
        testList = if (genre.isNotEmpty()) {
            mutableListOf(genre)
        } else {
            mutableListOf(year, season, sort)
        }
    }

    fun getAnimeList() {
        viewModelScope.launch {
            when (selectType) {
                Constant.SEASON_YEAR -> {
                    seasonYearUseCase(
                        page,
                        selectedSort!!,
                        selectedSeason!!,
                        selectedYear!!,
                        selectGenre
                    ).collect {
                        _animeListResponse.postValue(it)
                    }
                }

                Constant.NO_SEASON -> {
                    noSeasonUseCase(
                        page,
                        selectedSort!!,
                        selectedYear!!,
                        selectGenre
                    ).collect {
                        _noSeasonListResponse.postValue(it)
                    }
                }

                Constant.NO_SEASON_NO_YEAR -> {
                    noSeasonNoYearUseCase(
                        page,
                        selectedSort!!,
                        selectGenre
                    ).collect {
                        _noSeasonNoYearListResponse.postValue(it)
                    }
                }
            }
        }
    }

    fun getGenreList() {
        viewModelScope.launch {
            genreUseCase().collect {
                _genreListResponse.value = it
            }
        }
    }

    fun getObserver(): LiveData<*>? {
        return when (selectType) {
            Constant.SEASON_YEAR -> _animeListResponse
            Constant.NO_SEASON_NO_YEAR -> _noSeasonNoYearListResponse
            Constant.NO_SEASON -> _noSeasonListResponse
            else -> null
        }
    }

    fun refresh() {
        page = 1
        hasNextPage = true
        animeResultList.clear()
        getAnimeList()
    }

    fun clear() {
        animeResultList.clear()
        genreList.clear()
    }
}