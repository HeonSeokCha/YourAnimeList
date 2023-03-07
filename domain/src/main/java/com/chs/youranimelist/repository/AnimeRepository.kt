package com.chs.youranimelist.repository

import com.chs.youranimelist.model.*
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    suspend fun getAnimeRecommendList(): com.chs.youranimelist.model.AnimeRecommendList

    suspend fun getAnimeFilteredList(
        selectType: String,
        sortType: String,
        season: String,
        year: Int,
        genre: String?
    ): com.chs.youranimelist.model.ListInfo<com.chs.youranimelist.model.AnimeInfo>

    suspend fun getAnimeDetailInfo(animeId: Int): com.chs.youranimelist.model.AnimeDetailInfo

    suspend fun getAnimeDetailInfoRecommendList(
        page: Int,
        animeId: Int
    ): com.chs.youranimelist.model.ListInfo<com.chs.youranimelist.model.AnimeInfo>

    suspend fun getAnimeDetailTheme(animeId: Int): com.chs.youranimelist.model.AnimeThemeInfo

    suspend fun getAnimeSearchResult(
        page: Int,
        query: String
    ): com.chs.youranimelist.model.ListInfo<com.chs.youranimelist.model.AnimeInfo>

    fun getSavedAnimeList(): Flow<List<com.chs.youranimelist.model.AnimeInfo>>

    fun getSavedAnimeInfo(id: Int): Flow<com.chs.youranimelist.model.AnimeInfo?>

    suspend fun insertSavedAnimeInfo(animeInfo: com.chs.youranimelist.model.AnimeInfo)

    suspend fun deleteSavedAnimeInfo(animeInfo: com.chs.youranimelist.model.AnimeInfo)

    suspend fun getAnimeGenreList(): List<String>

}