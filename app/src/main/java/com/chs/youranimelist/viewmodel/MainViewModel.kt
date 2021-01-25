package com.chs.youranimelist.viewmodel

import androidx.lifecycle.*
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel (private val repository: AnimeRepository): ViewModel() {
    private val _netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    val netWorkState: StateFlow<NetWorkState> = _netWorkState
    private lateinit var viewPagerListQuery: AnimeRecListQuery.ViewPager

    sealed class NetWorkState {
        object Success: NetWorkState()
        data class Error(val message: String): NetWorkState()
        object Loading: NetWorkState()
        object Empty: NetWorkState()
    }

    fun getPagerAnimeList(): LiveData<AnimeRecListQuery.ViewPager> {
        val responseLiveData: MutableLiveData<AnimeRecListQuery.ViewPager> = MutableLiveData()
        responseLiveData.value = viewPagerListQuery
        return responseLiveData
    }

    fun getAnimeRecList():LiveData<List<*>> {
        val responseLiveData: MutableLiveData<List<*>> = MutableLiveData()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeRecList().catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect {
                viewPagerListQuery = it.viewPager!!
                responseLiveData.value = listOf (
                    it.trending?.trendingMedia,
                    it.popular?.popularMedia,
                    it.upcomming?.upcommingMedia,
                    it.alltime?.alltimeMedia,
                )
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }

    fun getAnimeList (
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: Input<MediaSeason>,
        seasonYear: Input<Int> ):LiveData<AnimeListQuery.Data> {
        val responseLiveData: MutableLiveData<AnimeListQuery.Data> = MutableLiveData()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeList(page, sort, season, seasonYear).catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect {
                responseLiveData.value = it
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }

    fun getAnimeInfo(animeId: Input<Int>):LiveData<AnimeDetailQuery.Media> {
        val responseLiveData: MutableLiveData<AnimeDetailQuery.Media> = MutableLiveData()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeInfo(animeId).catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect{
                responseLiveData.value = it.media
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }
}