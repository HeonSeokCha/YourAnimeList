package com.chs.youranimelist.ui.sortedlist

import androidx.lifecycle.*
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.AnimeListRepository
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
    private val repositoryImpl: AnimeListRepository
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

    fun getAnimeList() {
        viewModelScope.launch {
            when (selectType) {
                Constant.SEASON_YEAR -> {
                    _animeListResponse.postValue(NetworkState.Loading())
                    repositoryImpl.getAnimeList(
                        page.toInput(),
                        selectedSort.toInput(),
                        selectedSeason.toInput(),
                        selectedYear.toInput(),
                        selectGenre.toInput()
                    ).catch { e ->
                        _animeListResponse.postValue(NetworkState.Error(e.message.toString()))
                    }.collect {
                        _animeListResponse.postValue(NetworkState.Success(it.data?.page!!))
                    }
                }

                Constant.NO_SEASON -> {
                    _noSeasonListResponse.postValue(NetworkState.Loading())
                    repositoryImpl.getNoSeasonList(
                        page.toInput(),
                        selectedSort.toInput(),
                        selectedYear.toInput(),
                        selectGenre.toInput()
                    ).catch { e ->
                        _noSeasonListResponse.postValue(NetworkState.Error(e.message.toString()))
                    }.collect {
                        _noSeasonListResponse.postValue(NetworkState.Success(it.data?.page!!))
                    }
                }

                Constant.NO_SEASON_NO_YEAR -> {
                    _noSeasonNoYearListResponse.postValue(NetworkState.Loading())
                    repositoryImpl.getNoSeasonNoYearList(
                        page.toInput(),
                        selectedSort.toInput(),
                        selectGenre.toInput()
                    ).catch { e ->
                        _noSeasonNoYearListResponse.postValue(NetworkState.Error(e.message.toString()))
                    }.collect {
                        _noSeasonNoYearListResponse.postValue(NetworkState.Success(it.data?.page!!))
                    }
                }
            }
        }
    }

    fun getGenreList() {
        viewModelScope.launch {
            _genreListResponse.value = NetworkState.Loading()
            repositoryImpl.getGenre().catch { e ->
                _genreListResponse.value = NetworkState.Error(e.message.toString())
            }.collect { _genreListResponse.value = NetworkState.Success(it.data!!) }
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