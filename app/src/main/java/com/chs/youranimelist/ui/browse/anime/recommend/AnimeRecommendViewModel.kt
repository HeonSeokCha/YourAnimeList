package com.chs.youranimelist.ui.browse.anime.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnimeRecommendViewModel : ViewModel() {

    private val _animeRecommendResponse =
        MutableStateFlow<NetWorkState<AnimeRecommendQuery.Data>>(NetWorkState.Loading())
    val animeRecommendResponse: StateFlow<NetWorkState<AnimeRecommendQuery.Data>>
        get() = _animeRecommendResponse

    private val repository by lazy { AnimeRepository() }

    var animeRecList = ArrayList<AnimeRecommendQuery.Edge?>()
    var page: Int = 1
    var hasNextPage: Boolean = true

    fun getRecommendList(animeId: Int) {
        viewModelScope.launch {
            repository.getAnimeRecList(animeId.toInput(), page.toInput()).catch { e ->
                _animeRecommendResponse.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _animeRecommendResponse.value = NetWorkState.Success(it.data!!)
            }
        }
    }
}