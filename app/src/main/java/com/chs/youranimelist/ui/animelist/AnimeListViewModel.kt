package com.chs.youranimelist.ui.animelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.repository.AnimeListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeListViewModel(private val listRepository: AnimeListRepository) : ViewModel() {

    var animeList: List<Anime> = listOf()

    val animeListResponse by lazy {
        listRepository.animeListResponse
    }

    fun getAllAnimeList() {
        viewModelScope.launch {
            listRepository.getAllAnimeList()
        }
    }

    fun deleteAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.deleteAnimeList(anime)
        }
    }
}