package com.chs.youranimelist.ui.home

import androidx.lifecycle.*
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel (private val repository: AnimeRepository): ViewModel() {
    private val _netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    val netWorkState: StateFlow<NetWorkState> = _netWorkState
    private lateinit var viewPagerListQuery: List<AnimeRecListQuery.Medium?>

    sealed class NetWorkState {
        object Success: NetWorkState()
        data class Error(val message: String): NetWorkState()
        object Loading: NetWorkState()
        object Empty: NetWorkState()
    }

    fun getPagerAnimeList(): LiveData<List<AnimeRecListQuery.Medium?>> {
        val responseLiveData: MutableLiveData<List<AnimeRecListQuery.Medium?>> = MutableLiveData()
        responseLiveData.value = viewPagerListQuery
        return responseLiveData
    }

    fun getAnimeRecList():LiveData<List<List<AnimeList>>> {
        val responseLiveData: MutableLiveData<List<List<AnimeList>>> = MutableLiveData()
        val listAnime: MutableList<MutableList<AnimeList>> = mutableListOf()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeRecList().catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect {
                viewPagerListQuery = it.viewPager?.media!!
                it.trending?.trendingMedia.apply {
                    val anime: MutableList<AnimeList> = mutableListOf()
                     for(i in this!!.indices) {
                         anime.add(this[i]!!.fragments.animeList)
                     }
                    listAnime.add(anime)
                }
                it.popular?.popularMedia.apply {
                    val anime: MutableList<AnimeList> = mutableListOf()
                    for(i in this!!.indices) {
                        anime.add(this[i]!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
                it.upcomming?.upcommingMedia.apply {
                    val anime: MutableList<AnimeList> = mutableListOf()
                    for(i in this!!.indices) {
                        anime.add(this[i]!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
                it.alltime?.alltimeMedia.apply {
                    val anime: MutableList<AnimeList> = mutableListOf()
                    for(i in this!!.indices) {
                        anime.add(this[i]!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
                responseLiveData.value = listAnime
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }

    fun getAnimeList (
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: MediaSeason?,
        seasonYear: Input<Int> ):LiveData<List<AnimeList>> {
        val responseLiveData: MutableLiveData<List<AnimeList>> = MutableLiveData()
        val listAnime: MutableList<AnimeList> = mutableListOf()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeList(page, sort, season.toInput(), seasonYear).catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect {
                if(season == null) {
                    it.nonSeason!!.media!!.forEach { anime ->
                        listAnime.add(anime!!.fragments.animeList)
                    }
                } else {
                    it.season!!.media!!.forEach { anime ->
                        listAnime.add(anime!!.fragments.animeList)
                    }
                }
                responseLiveData.value = listAnime
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