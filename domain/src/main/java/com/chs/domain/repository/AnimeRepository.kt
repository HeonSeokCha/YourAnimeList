package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.domain.model.*
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    suspend fun getAnimeRecommendList(): AnimeRecommendList

    suspend fun getAnimeFilteredList(
        selectType: String,
        sortType: String,
        season: String,
        year: Int,
        genre: String?
    ): Flow<PagingData<AnimeInfo>>

    suspend fun getAnimeDetailInfo(animeId: Int): AnimeDetailInfo

    suspend fun getAnimeDetailInfoRecommendList(animeId: Int): Flow<PagingData<AnimeInfo>>

    suspend fun getAnimeDetailTheme(animeId: Int): AnimeThemeInfo

    suspend fun getAnimeSearchResult(query: String): Flow<PagingData<AnimeInfo>>

    fun getSavedAnimeList(): Flow<List<AnimeInfo>>

    fun getSavedAnimeInfo(id: Int): Flow<AnimeInfo?>

    suspend fun insertSavedAnimeInfo(animeInfo: AnimeInfo)

    suspend fun deleteSavedAnimeInfo(animeInfo: AnimeInfo)

    suspend fun getAnimeGenreList(): List<String>

}