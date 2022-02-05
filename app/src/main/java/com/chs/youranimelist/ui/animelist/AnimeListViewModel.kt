package com.chs.youranimelist.ui.animelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.data.domain.repository.YourAnimeListRepository
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val repositoryYourImpl: YourAnimeListRepository
) : ViewModel() {

    var animeList: List<Anime> = listOf()
    private val _animeListResponse = SingleLiveEvent<List<Anime>>()
    val animeListResponse: LiveData<List<Anime>> get() = _animeListResponse

    fun getAllAnimeList() {
        viewModelScope.launch {
            repositoryYourImpl.getAllAnimeList().catch {
                _animeListResponse.value = listOf()
            }.collect {
                _animeListResponse.value = it
            }
        }
    }

    fun searchAnimeList(animeTitle: String) {
        viewModelScope.launch {
            repositoryYourImpl.searchAnimeList(animeTitle).catch {
                _animeListResponse.value = listOf()
            }.collect {
                _animeListResponse.value = it
            }
        }
    }

}