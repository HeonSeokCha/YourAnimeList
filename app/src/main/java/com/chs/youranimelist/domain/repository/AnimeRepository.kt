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

    suspend fun getSavedAnimeList(): List<AnimeInfo>

    suspend fun getSavedAnimeInfo(): AnimeInfo?

    suspend fun insertSavedAnimeInfo(animeInfo: AnimeInfo)

    suspend fun deleteSavedAnimeInfo()

    suspend fun getAnimeGenreList()

}