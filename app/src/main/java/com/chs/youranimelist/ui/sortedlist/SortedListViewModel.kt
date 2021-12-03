package com.chs.youranimelist.ui.sortedlist

import android.util.Log
import androidx.lifecycle.*
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.GenreQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.type.MediaStatus
import com.chs.youranimelist.util.SingleLiveEvent
import io.ktor.client.features.*
import io.ktor.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SortedListViewModel : ViewModel() {

    private val _animeListResponse = SingleLiveEvent<NetWorkState<AnimeListQuery.Data>>()
    val animeListResponse: LiveData<NetWorkState<AnimeListQuery.Data>>
        get() = _animeListResponse

    private val _genreListResponse = SingleLiveEvent<NetWorkState<GenreQuery.Data>>()
    val genreListResponse: LiveData<NetWorkState<GenreQuery.Data>>
        get() = _genreListResponse

    private val animeListRepository by lazy { AnimeListRepository() }
    var selectedYear: Int? = null
    var selectedSeason: MediaSeason? = null
    var selectedSort: MediaSort? = null
    var selectStatus: MediaStatus? = MediaStatus.RELEASING
    var selectGenre: String? = null

    var page: Int = 1
    var hasNextPage: Boolean = true
    var isSeason: Boolean = false
    var animeResultList: ArrayList<AnimeList?> = ArrayList()
    var genreList: ArrayList<String> = ArrayList()

    fun getAnimeList() {
        viewModelScope.launch {
            _animeListResponse.value = NetWorkState.Loading()
            animeListRepository.getAnimeList(
                page.toInput(),
                selectedSort.toInput(),
                selectedSeason.toInput(),
                selectedYear.toInput(),
                selectStatus.toInput(),
                selectGenre.toInput()
            ).catch { e ->
                _animeListResponse.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _animeListResponse.value = NetWorkState.Success(it.data!!)
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