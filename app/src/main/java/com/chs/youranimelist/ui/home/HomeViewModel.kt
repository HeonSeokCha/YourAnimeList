package com.chs.youranimelist.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val animeRepository: AnimeRepository) : ViewModel() {

    private val _animeRecUiState: MutableStateFlow<NetWorkState<ArrayList<ArrayList<AnimeList>>>> =
        MutableStateFlow(NetWorkState.Loading())
    val animeRecListUiState = _animeRecUiState.asStateFlow()

    var pagerRecList = ArrayList<HomeRecommendListQuery.Medium?>()


    fun getAnimeRecList() {
        val listAnime = ArrayList<ArrayList<AnimeList>>()
        viewModelScope.launch {
            _animeRecUiState.value = NetWorkState.Loading()
            animeRepository.getHomeRecList().catch { e ->
                _animeRecUiState.value = NetWorkState.Error(e.message.toString())
            }.collect {
                it.viewPager?.media!!.forEach { pager ->
                    pagerRecList.add(pager)
                }
                it.trending?.trendingMedia.apply {
                    val anime: ArrayList<AnimeList> = ArrayList()
                    this!!.forEach { trending ->
                        anime.add(trending!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
                it.popular?.popularMedia.apply {
                    val anime: ArrayList<AnimeList> = ArrayList()
                    this!!.forEach { popular ->
                        anime.add(popular!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
                it.upcomming?.upcommingMedia.apply {
                    val anime: ArrayList<AnimeList> = ArrayList()
                    this!!.forEach { upComming ->
                        anime.add(upComming!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
                it.alltime?.alltimeMedia.apply {
                    val anime: ArrayList<AnimeList> = ArrayList()
                    this!!.forEach { allTime ->
                        anime.add(allTime!!.fragments.animeList)
                    }
                    listAnime.add(anime)
                }
            }
            _animeRecUiState.value = NetWorkState.Success(listAnime)
        }
    }
}