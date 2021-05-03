package com.chs.youranimelist.ui.animelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.repository.AnimeListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeListViewModel(private val listRepository: AnimeListRepository) : ViewModel() {

    fun getAllAnimeList(): LiveData<List<Anime>> = listRepository.getAllAnimeList()

    fun deleteAnimeList(anime: Anime) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.deleteAnimeList(anime)
        }
    }
}