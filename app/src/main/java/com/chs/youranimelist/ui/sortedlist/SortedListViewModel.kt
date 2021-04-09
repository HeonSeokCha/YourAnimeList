package com.chs.youranimelist.ui.sortedlist

import androidx.lifecycle.*
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.launch

class SortedListViewModel(private val animeListRepository: AnimeListRepository) : ViewModel() {

    var selectedYear: Int? = null
    var selectedSeason: MediaSeason? = null
    var selectedSort: MediaSort? = null

    var page: Int = 1
    var hasNextPage: Boolean = true
    var isSeason: Boolean = false
    var animeResultList: ArrayList<AnimeList?> = ArrayList()

    val animeListResponse by lazy {
        animeListRepository.animeListResponse
    }

    val animeSortArray = arrayOf(
        "NEWEST",
        "OLDEST",
        "TITLE ROMAJI",
        "TITLE ENGLISH",
        "TITLE NATIVE",
        "HIGHEST SCORE",
        "LOWEST SCORE",
        "MOST POPULAR",
        "LEAST POPULAR",
        "MOST FAVORITE",
        "LEAST FAVORITE",
        "TRENDING"
    )

    var animeSortList = arrayListOf(
        MediaSort.START_DATE_DESC,
        MediaSort.START_DATE,
        MediaSort.TITLE_ROMAJI,
        MediaSort.TITLE_ENGLISH,
        MediaSort.TITLE_NATIVE,
        MediaSort.SCORE_DESC,
        MediaSort.SCORE,
        MediaSort.POPULARITY_DESC,
        MediaSort.POPULARITY,
        MediaSort.FAVOURITES_DESC,
        MediaSort.FAVOURITES,
        MediaSort.TRENDING_DESC
    )

    val animeSeasonList = arrayListOf(
        MediaSeason.WINTER, MediaSeason.SPRING, MediaSeason.SUMMER, MediaSeason.FALL
    )

    fun getAnimeList() {
        viewModelScope.launch {
            animeListRepository.getAnimeList(
                page.toInput(),
                selectedSort.toInput(),
                selectedSeason.toInput(),
                selectedYear.toInput()
            )
        }
    }

    fun refresh() {
        page = 1
        hasNextPage = true
        animeResultList.clear()
        getAnimeList()
    }
}