package com.chs.youranimelist.domain.repository

import com.chs.youranimelist.domain.model.*
import kotlinx.coroutines.flow.Flow

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

    fun getSavedAnimeList(): Flow<List<AnimeInfo>>

    fun getSavedAnimeInfo(id: Int): Flow<AnimeInfo?>

    suspend fun insertSavedAnimeInfo(animeInfo: AnimeInfo)

    suspend fun deleteSavedAnimeInfo(animeInfo: AnimeInfo)

    suspend fun getAnimeGenreList(): List<String>

}