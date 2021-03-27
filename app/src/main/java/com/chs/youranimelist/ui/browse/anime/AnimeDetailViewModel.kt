package com.chs.youranimelist.ui.browse.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeDetailViewModel(private val repository: AnimeRepository) : ViewModel() {

    var animeDetail: AnimeDetailQuery.Media? = null

    val animeDetailResponse by lazy {
        repository.animeDetailResponse
    }

    fun getAnimeInfo(animeId: Input<Int>) {
        viewModelScope.launch {
            repository.getAnimeDetail(animeId)
        }
    }
}