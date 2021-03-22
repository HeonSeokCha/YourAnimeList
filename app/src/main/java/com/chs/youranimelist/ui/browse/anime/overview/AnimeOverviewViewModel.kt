package com.chs.youranimelist.ui.browse.anime.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeOverviewViewModel(private val repository: AnimeRepository) : ViewModel() {

    val animeOverviewResponse by lazy {
        repository.animeOverviewResponse
    }

    var animeOverviewRelationList = ArrayList<AnimeOverviewQuery.RelationsEdge?>()

    var animeGenresList = ArrayList<String>()

    fun getAnimeOverview(animeId: Int) {
        viewModelScope.launch {
            repository.getAnimeOverview(animeId.toInput())
        }
    }
}