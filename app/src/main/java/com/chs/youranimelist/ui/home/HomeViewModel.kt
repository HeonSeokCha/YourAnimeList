package com.chs.youranimelist.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val animeRepository: AnimeRepository) : ViewModel() {
    private lateinit var viewPagerListQuery: List<HomeRecommendListQuery.Medium?>

    fun getPagerAnimeList(): LiveData<List<HomeRecommendListQuery.Medium?>> {
        val responseLiveData: MutableLiveData<List<HomeRecommendListQuery.Medium?>> =
            MutableLiveData()
        responseLiveData.value = viewPagerListQuery
        return responseLiveData
    }

    @ExperimentalCoroutinesApi
    fun getAnimeRecList(): LiveData<NetWorkState<List<List<AnimeList>>>> {
        val responseLiveData: MutableLiveData<NetWorkState<List<List<AnimeList>>>> =
            MutableLiveData()
        val listAnime: MutableList<MutableList<AnimeList>> = mutableListOf()
        viewModelScope.launch {
            responseLiveData.postValue(NetWorkState.Loading())
            animeRepository.getAnimeRecList().catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
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
                responseLiveData.postValue(NetWorkState.Success(listAnime))
            }
        }
        return responseLiveData
    }
}