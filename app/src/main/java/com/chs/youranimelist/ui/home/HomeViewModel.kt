package com.chs.youranimelist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.home.HomeRecommendListQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _homeRecommendResponse =
        SingleLiveEvent<NetWorkState<HomeRecommendListQuery.Data>>()
    val homeRecommendResponse: LiveData<NetWorkState<HomeRecommendListQuery.Data>>
        get() = _homeRecommendResponse

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