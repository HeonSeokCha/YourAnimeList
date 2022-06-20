package com.chs.youranimelist.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.domain.usecase.GetHomeRecListUseCase
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeListUseCase: GetHomeRecListUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    init {
        getHomeRecList()
    }

    private fun getHomeRecList() {
        viewModelScope.launch {
            getHomeListUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(pagerList = result.data?.viewPager?.media!!)
                        result.data.trending?.trendingMedia.apply {
                            val anime: ArrayList<AnimeList> = ArrayList()
                            this!!.forEach { trending ->
                                anime.add(trending!!.animeList)
                            }
                            state.nestedList.add(anime)
                        }
                        result.data.popular?.popularMedia.apply {
                            val anime: ArrayList<AnimeList> = ArrayList()
                            this!!.forEach { popular ->
                                anime.add(popular!!.animeList)
                            }
                            state.nestedList.add(anime)
                        }
                        result.data.upComming?.upCommingMedia.apply {
                            val anime: ArrayList<AnimeList> = ArrayList()
                            this!!.forEach { upComming ->
                                anime.add(upComming!!.animeList)
                            }
                            state.nestedList.add(anime)
                        }
                        result.data.allTime?.allTimeMedia.apply {
                            val anime: ArrayList<AnimeList> = ArrayList()
                            this!!.forEach { allTime ->
                                anime.add(allTime!!.animeList)
                            }
                            state.nestedList.add(anime)
                        }
                        state = state.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }
}