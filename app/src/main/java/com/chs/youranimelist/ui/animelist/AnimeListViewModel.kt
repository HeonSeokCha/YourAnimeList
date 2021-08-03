package com.chs.youranimelist.ui.animelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.repository.AnimeListRepository
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeListViewModel(private val listRepository: AnimeListRepository) : ViewModel() {

    var animeList: List<Anime> = listOf()

    private val _animeListResponse = SingleLiveEvent<List<Anime>>()
    val animeListResponse: LiveData<List<Anime>> get() = _animeListResponse

    fun getAllAnimeList() {
        viewModelScope.launch {
            listRepository.getAllAnimeList().catch {
                _animeListResponse.value = listOf()
            }.collect {
                _animeListResponse.value = it
            }
        }
    }

    fun searchAnimeList(animeTitle: String) {
        viewModelScope.launch {
            listRepository.searchAnimeList(animeTitle).catch {
                _animeListResponse.value = listOf()
            }.collect {
                _animeListResponse.value = it
            }
        }
    }

    fun deleteAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.deleteAnimeList(anime)
        }
    }
}