package com.chs.youranimelist.ui.browse.anime

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.repository.AnimeListRepository
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val repository: AnimeRepository,
    application: Application
) : ViewModel() {

    private val _animeDetailResponse = SingleLiveEvent<NetWorkState<AnimeDetailQuery.Data>>()
    val animeDetailResponse: LiveData<NetWorkState<AnimeDetailQuery.Data>>
        get() = _animeDetailResponse

    private val animeRepository: AnimeListRepository by lazy { AnimeListRepository(application) }
    var animeDetail: AnimeDetailQuery.Media? = null
    var initAnimeList: Anime? = null

    fun getAnimeDetail(animeId: Input<Int>) {
        _animeDetailResponse.postValue(NetWorkState.Loading())
        viewModelScope.launch {
            repository.getAnimeDetail(animeId).catch { e ->
                _animeDetailResponse.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                _animeDetailResponse.postValue(NetWorkState.Success(it.data!!))
            }
        }
    }

    fun checkAnimeList(animeId: Int): LiveData<List<Anime>> =
        animeRepository.checkAnimeList(animeId)

    fun insertAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            animeRepository.insertAnimeList(anime)
        }
    }

    fun deleteAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            animeRepository.deleteAnimeList(anime)
        }
    }
}