package com.chs.youranimelist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _homeRecommendResponse =
        SingleLiveEvent<NetWorkState<HomeRecommendListQuery.Data>>()
    val homeRecommendResponse: LiveData<NetWorkState<HomeRecommendListQuery.Data>>
        get() = _homeRecommendResponse

    private val animeRepository by lazy { AnimeRepository() }
    var pagerRecList = ArrayList<HomeRecommendListQuery.Medium>()
    var homeRecList = ArrayList<ArrayList<AnimeList>>()


    fun getHomeRecList() {
        viewModelScope.launch {
            _homeRecommendResponse.value = NetWorkState.Loading()
            animeRepository.getHomeRecList()
                .catch { e ->
                    _homeRecommendResponse.value = NetWorkState.Error(e.message.toString())
                }.collect {
                    _homeRecommendResponse.value = NetWorkState.Success(it.data!!)
                }
        }
    }
}