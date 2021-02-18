package com.chs.youranimelist.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val animeRepository: AnimeRepository) : ViewModel() {
    private val _netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    private lateinit var viewPagerListQuery: List<AnimeRecListQuery.Medium?>
    val netWorkState: StateFlow<NetWorkState> = _netWorkState

    sealed class NetWorkState {
        object Success : NetWorkState()
        data class Error(val message: String) : NetWorkState()
        object Loading : NetWorkState()
        object Empty : NetWorkState()
    }

    fun getPagerAnimeList(): LiveData<List<AnimeRecListQuery.Medium?>> {
        val responseLiveData: MutableLiveData<List<AnimeRecListQuery.Medium?>> = MutableLiveData()
        responseLiveData.value = viewPagerListQuery
        return responseLiveData
    }

    @ExperimentalCoroutinesApi
    fun getAnimeRecList(): LiveData<List<List<AnimeList>>> {
        val responseLiveData: MutableLiveData<List<List<AnimeList>>> = MutableLiveData()
        val listAnime: MutableList<MutableList<AnimeList>> = mutableListOf()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            animeRepository.getAnimeRecList().catch { e ->
                _netWorkState.value = NetWorkState.Error(e.toString())
                Log.d("error", "$e")
            }.collect {
                viewPagerListQuery = it.viewPager?.media!!
                it.trending?.trendingMedia.apply {
                    val anime: MutableList<AnimeList> = mutableListOf()
                    for (i in this!!.indices) {
                        anime.add(this[i]!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
                it.popular?.popularMedia.apply {
                    val anime: MutableList<AnimeList> = mutableListOf()
                    for (i in this!!.indices) {
                        anime.add(this[i]!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
                it.upcomming?.upcommingMedia.apply {
                    val anime: MutableList<AnimeList> = mutableListOf()
                    for (i in this!!.indices) {
                        anime.add(this[i]!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
                it.alltime?.alltimeMedia.apply {
                    val anime: MutableList<AnimeList> = mutableListOf()
                    for (i in this!!.indices) {
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
}