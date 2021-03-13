package com.chs.youranimelist.ui.list

import androidx.lifecycle.*
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnimeListViewModel(private val animeListRepository: AnimeListRepository) : ViewModel() {
    var page: Int = 1
    var hasNextPage: Boolean = true
    var animeResultList: ArrayList<AnimeList?> = ArrayList()
    private val _uiState: MutableStateFlow<NetWorkState<AnimeListQuery.Data>> =
        MutableStateFlow(NetWorkState.Loading())
    val uiState = _uiState.asStateFlow()

    fun getAnimeList(
        sort: Input<MediaSort>,
        season: MediaSeason?,
        seasonYear: Input<Int>
    ) {
        viewModelScope.launch {
            _uiState.value = NetWorkState.Loading()
            animeListRepository.getAnimeList(page.toInput(), sort, season.toInput(), seasonYear)
                .catch { e ->
                    _uiState.value = NetWorkState.Error(e.message.toString())
                }.collect {
                    _uiState.value = NetWorkState.Success(it)
                }
        }
    }
}