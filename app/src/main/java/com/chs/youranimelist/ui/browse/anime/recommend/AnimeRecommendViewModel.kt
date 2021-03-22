package com.chs.youranimelist.ui.browse.anime.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeRecommendViewModel(private val repository: AnimeRepository) : ViewModel() {

    val animeRecommendResponse by lazy {
        repository.animeRecommendResponse
    }

    var animeRecList = ArrayList<AnimeRecommendQuery.Edge?>()

    fun getRecommendList(animeId: Int) {
        viewModelScope.launch {
            repository.getAnimeRecList(animeId.toInput())
        }
    }
}