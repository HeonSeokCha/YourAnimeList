package com.chs.youranimelist.ui.browse.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.data.domain.repository.YourAnimeListRepository
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.repository.AnimeRepository
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val animeListRepository: YourAnimeListRepository
) : ViewModel() {

    private val _animeDetailResponse = SingleLiveEvent<NetWorkState<AnimeDetailQuery.Data>>()
    val animeDetailResponse: LiveData<NetWorkState<AnimeDetailQuery.Data>>
        get() = _animeDetailResponse


    var animeDetail: AnimeDetailQuery.Media? = null
    var initAnimeList: Anime? = null

    fun getAnimeDetail(animeId: Input<Int>) {
        _animeDetailResponse.value = NetWorkState.Loading()
        viewModelScope.launch {
            animeRepository.getAnimeDetail(animeId).catch { e ->
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