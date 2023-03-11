package com.chs.presentation.repository

import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    suspend fun getAnimeRecommendList(): com.chs.presentation.model.AnimeRecommendList

    suspend fun getAnimeFilteredList(
        selectType: String,
        sortType: String,
        season: String,
        year: Int,
        genre: String?
    ): com.chs.presentation.model.ListInfo<com.chs.presentation.model.AnimeInfo>

    suspend fun getAnimeDetailInfo(animeId: Int): com.chs.presentation.model.AnimeDetailInfo

    suspend fun getAnimeDetailInfoRecommendList(
        page: Int,
        animeId: Int
    ): com.chs.presentation.model.ListInfo<com.chs.presentation.model.AnimeInfo>

    suspend fun getAnimeDetailTheme(animeId: Int): com.chs.presentation.model.AnimeThemeInfo

    suspend fun getAnimeSearchResult(
        page: Int,
        query: String
    ): com.chs.presentation.model.ListInfo<com.chs.presentation.model.AnimeInfo>

    fun getSavedAnimeList(): Flow<List<com.chs.presentation.model.AnimeInfo>>

    fun getSavedAnimeInfo(id: Int): Flow<com.chs.presentation.model.AnimeInfo?>

    suspend fun insertSavedAnimeInfo(animeInfo: com.chs.presentation.model.AnimeInfo)

    suspend fun deleteSavedAnimeInfo(animeInfo: com.chs.presentation.model.AnimeInfo)

    suspend fun getAnimeGenreList(): List<String>

}