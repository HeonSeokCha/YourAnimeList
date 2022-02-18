package com.chs.youranimelist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.home.HomeRecommendListQuery
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.usecase.GetHomeRecListUseCase
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeRecListUseCase: GetHomeRecListUseCase
) : ViewModel() {

    private val _homeRecommendResponse =
        SingleLiveEvent<NetworkState<HomeRecommendListQuery.Data>>()
    val homeRecommendResponse: LiveData<NetworkState<HomeRecommendListQuery.Data>>
        get() = _homeRecommendResponse

    var pagerRecList = ArrayList<HomeRecommendListQuery.Medium>()
    var homeRecList = ArrayList<ArrayList<AnimeList>>()


    fun getHomeRecList() {
        viewModelScope.launch {
            getHomeRecListUseCase().collect {
                _homeRecommendResponse.value = it
            }
        }
    }
}