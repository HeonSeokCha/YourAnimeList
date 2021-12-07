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

    private val _animeListResponse = SingleLiveEvent<NetWorkState<AnimeListQuery.Page>>()
    val animeListResponse: LiveData<NetWorkState<AnimeListQuery.Page>>
        get() = _animeListResponse

    private val _noSeasonNoYearListResponse =
        SingleLiveEvent<NetWorkState<NoSeasonNoYearQuery.Page>>()
    val noSeasonNoYearListResponse: LiveData<NetWorkState<NoSeasonNoYearQuery.Page>>
        get() = _noSeasonNoYearListResponse

    private val _noSeasonListResponse =
        SingleLiveEvent<NetWorkState<NoSeasonQuery.Page>>()
    val noSeasonListResponse: LiveData<NetWorkState<NoSeasonQuery.Page>>
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
                        _animeListResponse.postValue(NetWorkState.Error(e.message.toString()))
                    }.collect {
                        _animeListResponse.postValue(NetWorkState.Success(it.data?.page!!))
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
                        _noSeasonListResponse.postValue(NetWorkState.Error(e.message.toString()))
                    }.collect {
                        _noSeasonListResponse.postValue(NetWorkState.Success(it.data?.page!!))
                    }
                }

                Constant.NO_SEASON_NO_YEAR -> {
                    _noSeasonNoYearListResponse.value = NetWorkState.Loading()
                    animeListRepository.getNoSeasonNoYearList(
                        page.toInput(),
                        selectedSort.toInput(),
                        selectGenre.toInput()
                    ).catch { e ->
                        _noSeasonNoYearListResponse.postValue(NetWorkState.Error(e.message.toString()))
                    }.collect {
                        _noSeasonNoYearListResponse.postValue(NetWorkState.Success(it.data?.page!!))
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