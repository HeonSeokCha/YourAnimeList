package com.chs.youranimelist.ui.sortedlist

import androidx.lifecycle.*
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.GenreQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.type.MediaStatus
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SortedListViewModel : ViewModel() {

    private val _animeListResponse = SingleLiveEvent<NetWorkState<AnimeListQuery.Data>>()
    val animeListResponse: LiveData<NetWorkState<AnimeListQuery.Data>>
        get() = _animeListResponse

    private val _noSeasonNoYearListResponse =
        SingleLiveEvent<NetWorkState<NoSeasonNoYearQuery.Data>>()
    val noSeasonNoYearListResponse: LiveData<NetWorkState<NoSeasonNoYearQuery.Data>>
        get() = _noSeasonNoYearListResponse

    private val _noSeasonListResponse =
        SingleLiveEvent<NetWorkState<NoSeasonQuery.Data>>()
    val noSeasonListResponse: LiveData<NetWorkState<NoSeasonQuery.Data>>
        get() = _noSeasonListResponse

    private val _genreListResponse = SingleLiveEvent<NetWorkState<GenreQuery.Data>>()
    val genreListResponse: LiveData<NetWorkState<GenreQuery.Data>>
        get() = _genreListResponse

    private val animeListRepository by lazy { AnimeListRepository() }
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

    fun getAnimeList() {
        viewModelScope.launch {
            when (selectType) {
                Constant.SEASON_YEAR -> {
                    _animeListResponse.value = NetWorkState.Loading()
                    animeListRepository.getAnimeList(
                        page.toInput(),
                        selectedSort.toInput(),
                        selectedSeason.toInput(),
                        selectedYear.toInput(),
                        selectGenre.toInput()
                    ).catch { e ->
                        _animeListResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _animeListResponse.value = NetWorkState.Success(it.data!!)
                    }
                }

                Constant.NO_SEASON -> {
                    _noSeasonListResponse.value = NetWorkState.Loading()
                    animeListRepository.getNoSeasonList(
                        page.toInput(),
                        selectedSort.toInput(),
                        selectedYear.toInput(),
                        selectGenre.toInput()
                    ).catch { e ->
                        _noSeasonListResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _noSeasonListResponse.value = NetWorkState.Success(it.data!!)
                    }
                }

                Constant.NO_SEASON_NO_YEAR -> {
                    _noSeasonNoYearListResponse.value = NetWorkState.Loading()
                    animeListRepository.getNoSeasonNoYearList(
                        page.toInput(),
                        selectedSort.toInput(),
                        selectGenre.toInput()
                    ).catch { e ->
                        _noSeasonNoYearListResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _noSeasonNoYearListResponse.value = NetWorkState.Success(it.data!!)
                    }
                }
            }
        }
    }

    fun getGenreList() {
        viewModelScope.launch {
            _genreListResponse.value = NetWorkState.Loading()
            animeListRepository.getGenre().catch { e ->
                _genreListResponse.value = NetWorkState.Error(e.message.toString())
            }.collect { _genreListResponse.value = NetWorkState.Success(it.data!!) }
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