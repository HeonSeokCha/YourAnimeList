package com.chs.youranimelist.ui.sortedlist

import androidx.lifecycle.*
import com.apollographql.apollo3.api.toInput
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.AnimeListRepository
import com.chs.youranimelist.data.remote.usecase.GetGenreUseCase
import com.chs.youranimelist.data.remote.usecase.GetNoSeasonNoYearUseCase
import com.chs.youranimelist.data.remote.usecase.GetNoSeasonUseCase
import com.chs.youranimelist.data.remote.usecase.GetSeasonYearUseCase
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.GenreQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
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
    val animeListResponse: LiveData<NetworkState<AnimeListQuery.Page>>
        get() = _animeListResponse

    private val _noSeasonNoYearListResponse =
        SingleLiveEvent<NetworkState<NoSeasonNoYearQuery.Page>>()
    val noSeasonNoYearListResponse: LiveData<NetworkState<NoSeasonNoYearQuery.Page>>
        get() = _noSeasonNoYearListResponse

    private val _noSeasonListResponse =
        SingleLiveEvent<NetworkState<NoSeasonQuery.Page>>()
    val noSeasonListResponse: LiveData<NetworkState<NoSeasonQuery.Page>>
        get() = _noSeasonListResponse

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

    fun getFilterList(
        year: String = "",
        season: String = "",
        sort: String = "",
        genre: String = ""
    ): List<Pair<String, String>> {
        return if (genre.isNotEmpty()) {
            listOf(Pair("Genre", ""))
        } else {
            listOf(
                Pair("Year", year),
                Pair("Season", season),
                Pair("Sort", sort)
            )
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
            Constant.SEASON_YEAR -> animeListResponse
            Constant.NO_SEASON_NO_YEAR -> noSeasonNoYearListResponse
            Constant.NO_SEASON -> noSeasonListResponse
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