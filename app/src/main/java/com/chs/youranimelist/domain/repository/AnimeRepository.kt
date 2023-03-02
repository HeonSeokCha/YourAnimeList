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
    ): ListInfo<AnimeInfo>

    suspend fun getAnimeDetailInfo(animeId: Int): AnimeDetailInfo

    suspend fun getAnimeDetailInfoRecommendList(
        page: Int,
        animeId: Int
    ): ListInfo<AnimeInfo>

    suspend fun getAnimeDetailTheme(animeId: Int): AnimeThemeInfo

    suspend fun getAnimeSearchResult(
        page: Int,
        query: String
    ): ListInfo<AnimeInfo>

    suspend fun getSavedAnimeList()

    suspend fun getSavedAnimeInfo()

    suspend fun insertSavedAnimeInfo()

    suspend fun deleteSavedAnimeInfo()

    suspend fun getAnimeGenreList()

}