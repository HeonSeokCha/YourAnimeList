package com.chs.youranimelist.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val animeRepository: AnimeRepository) : ViewModel() {


    val animePagerRecommendResponse by lazy {
        animeRepository.homeRecommendResponse
    }

    var pagerRecList = ArrayList<HomeRecommendListQuery.Medium>()
    var homeRecList = ArrayList<ArrayList<AnimeList>>()


    fun getHomeRecList() {
        viewModelScope.launch {
            animeRepository.getHomeRecList()
        }
    }
}