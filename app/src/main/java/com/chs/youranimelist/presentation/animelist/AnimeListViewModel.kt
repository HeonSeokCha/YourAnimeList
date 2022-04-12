package com.chs.youranimelist.presentation.animelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.model.Anime
import com.chs.youranimelist.domain.usecase.GetYourAnimeListUseCase
import com.chs.youranimelist.domain.usecase.SearchYourAnimeListUseCase
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getAnimeUseCase: GetYourAnimeListUseCase,
    private val searchAnimeUseCase: SearchYourAnimeListUseCase
) : ViewModel() {

    var animeList: List<Anime> = listOf()
    private val _animeListResponse = SingleLiveEvent<List<Anime>>()
    val animeListResponse: LiveData<List<Anime>> get() = _animeListResponse

    fun getAllAnimeList() {
        viewModelScope.launch {
            getAnimeUseCase().catch {
                _animeListResponse.value = listOf()
            }.collect {
                _animeListResponse.value = it
            }
        }
    }

    fun searchAnimeList(animeTitle: String) {
        viewModelScope.launch {
            searchAnimeUseCase(animeTitle).catch {
                _animeListResponse.value = listOf()
            }.collect {
                _animeListResponse.value = it
            }
        }
    }
}