package com.chs.youranimelist.ui.browse.anime

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.repository.AnimeListRepository
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeDetailViewModel(application: Application) : ViewModel() {

    private val animeDetailRepository: AnimeRepository by lazy { AnimeRepository() }
    private val animeListRepository: AnimeListRepository by lazy { AnimeListRepository(application) }

    private val _animeDetailResponse =
        MutableStateFlow<NetWorkState<AnimeDetailQuery.Data>>(NetWorkState.Loading())
    val animeDetailResponse: StateFlow<NetWorkState<AnimeDetailQuery.Data>>
        get() = _animeDetailResponse


    var animeDetail: AnimeDetailQuery.Media? = null
    var initAnimeList: Anime? = null

    fun getAnimeDetail(animeId: Input<Int>) {
        viewModelScope.launch {
            animeDetailRepository.getAnimeDetail(animeId).catch { e ->
                _animeDetailResponse.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _animeDetailResponse.value = NetWorkState.Success(it.data!!)
            }
        }
    }

    fun checkAnimeList(animeId: Int): LiveData<Anime> =
        animeListRepository.checkAnimeList(animeId).asLiveData()

    fun insertAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            animeListRepository.insertAnimeList(anime)
        }
    }

    fun deleteAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            animeListRepository.deleteAnimeList(anime)
        }
    }
}