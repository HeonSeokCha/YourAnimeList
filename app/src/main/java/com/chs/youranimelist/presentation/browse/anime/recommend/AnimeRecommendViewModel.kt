package com.chs.youranimelist.presentation.browse.anime.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.usecase.GetAnimeRecUseCase
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeRecommendViewModel @Inject constructor(
    private val getAnimeRecUseCase: GetAnimeRecUseCase
) : ViewModel() {

    private val _animeRecommendResponse = SingleLiveEvent<NetworkState<AnimeRecommendQuery.Data>>()
    val animeRecommendResponse: LiveData<NetworkState<AnimeRecommendQuery.Data>>
        get() = _animeRecommendResponse

    var animeRecList = ArrayList<AnimeRecommendQuery.Edge?>()
    var page: Int = 1
    var hasNextPage: Boolean = true

    fun getRecommendList(animeId: Int) {
        viewModelScope.launch {
            getAnimeRecUseCase(animeId, page).collect {
                _animeRecommendResponse.value = it
            }
        }
    }
}