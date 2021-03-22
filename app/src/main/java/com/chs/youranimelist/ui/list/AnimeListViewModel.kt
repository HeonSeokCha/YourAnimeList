package com.chs.youranimelist.ui.list

import androidx.lifecycle.*
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnimeListViewModel(private val animeListRepository: AnimeListRepository) : ViewModel() {

    var page: Int = 1
    var hasNextPage: Boolean = true
    var season: Boolean = true
    var animeResultList: ArrayList<AnimeList?> = ArrayList()

    val animeListResponse by lazy {
        animeListRepository.animeListResponse
    }

    fun getAnimeList(
        sort: MediaSort,
        season: MediaSeason?,
        seasonYear: Int
    ) {
        viewModelScope.launch {
            animeListRepository.getAnimeList(
                page.toInput(), sort.toInput(), season.toInput(), seasonYear.toInput()
            )
        }
    }
}