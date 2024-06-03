package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeRecommendList
import com.chs.domain.model.AnimeThemeInfo
import kotlinx.coroutines.flow.Flow

interface AnimeRepository : BaseMediaRepository<AnimeInfo> {

    suspend fun getAnimeRecommendList(
        currentSeason: String,
        nextSeason: String,
        currentYear: Int,
        lastYear: Int,
        nextYear: Int
    ): AnimeRecommendList

    fun getAnimeFilteredList(
        sortType: List<String>,
        season: String?,
        year: Int?,
        genre: String?
    ): Flow<PagingData<AnimeInfo>>

    suspend fun getAnimeDetailInfo(animeId: Int): AnimeDetailInfo

    fun getAnimeDetailInfoRecommendList(animeId: Int): Flow<PagingData<AnimeInfo>>

    suspend fun getAnimeDetailTheme(animeId: Int): AnimeThemeInfo

    suspend fun getRecentGenreList()

    suspend fun getSavedGenreList(): List<String>

    override fun getSavedMediaInfoList(): Flow<List<AnimeInfo>>

    override suspend fun deleteMediaInfo(info: AnimeInfo)

    override fun getSavedMediaInfo(id: Int): Flow<AnimeInfo?>

    override suspend fun insertMediaInfo(info: AnimeInfo)
}