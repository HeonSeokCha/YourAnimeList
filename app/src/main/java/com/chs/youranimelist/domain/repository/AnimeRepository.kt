package com.chs.youranimelist.domain.repository

import com.chs.youranimelist.domain.model.*

interface AnimeRepository {

    suspend fun getAnimeRecommendList(): AnimeRecommendList

    suspend fun getAnimeFilteredList(
        selectType: String,
        sortType: String,
        season: String,
        year: Int,
        genre: String?
    ): List<AnimeInfo>

    suspend fun getAnimeDetailInfo(animeId: Int): AnimeDetailInfo

    suspend fun getAnimeDetailInfoRecommendList(animeId: Int): List<AnimeInfo>

    suspend fun getAnimeDetailTheme(animeId: Int): AnimeThemeInfo

    suspend fun getAnimeSearchResult(title: String): List<AnimeInfo>

    suspend fun getSavedAnimeList()

    suspend fun getSavedAnimeInfo()

    suspend fun insertSavedAnimeInfo()

    suspend fun deleteSavedAnimeInfo()

    suspend fun getAnimeGenreList()

}