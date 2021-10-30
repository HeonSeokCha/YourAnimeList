package com.chs.youranimelist.ui.animelist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.repository.AnimeListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeListViewModel(application: Application) : ViewModel() {

    private val listRepository: AnimeListRepository by lazy { AnimeListRepository(application) }

    var animeList: List<Anime> = listOf()

    private val _animeListResponse = MutableStateFlow<List<Anime>>(listOf())
    val animeListResponse: StateFlow<List<Anime>> get() = _animeListResponse

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

}