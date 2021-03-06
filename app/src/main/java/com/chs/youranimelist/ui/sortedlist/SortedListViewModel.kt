package com.chs.youranimelist.ui.sortedlist

import androidx.lifecycle.*
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.type.MediaStatus
import kotlinx.coroutines.launch

class SortedListViewModel(
    private val animeListRepository: AnimeListRepository
) : ViewModel() {

    var selectedYear: Int? = null
    var selectedSeason: MediaSeason? = null
    var selectedSort: MediaSort? = null
    var selectStatus: MediaStatus? = null
    var selectGenre: String? = null

    var page: Int = 1
    var hasNextPage: Boolean = true
    var isSeason: Boolean = false
    var animeResultList: ArrayList<AnimeList?> = ArrayList()
    var genreList: ArrayList<String> = ArrayList()

    val animeListResponse by lazy {
        animeListRepository.animeListResponse
    }

    val genreListResponse by lazy {
        animeListRepository.genreListResponse
    }

    val animeSeasonList = arrayListOf(
        MediaSeason.WINTER, MediaSeason.SPRING, MediaSeason.SUMMER, MediaSeason.FALL
    )

    fun getAnimeList() {
        viewModelScope.launch {
            animeListRepository.getAnimeList(
                page.toInput(),
                selectedSort.toInput(),
                selectedSeason.toInput(),
                selectedYear.toInput(),
                selectStatus.toInput(),
                selectGenre.toInput()
            )
        }
    }

    fun getGenreList() {
        viewModelScope.launch {
            animeListRepository.getGenre()
        }
    }

    fun refresh() {
        page = 1
        hasNextPage = true
        animeResultList.clear()
        getAnimeList()
    }

    fun clear() {
        animeResultList.clear()
        genreList.clear()
    }
}