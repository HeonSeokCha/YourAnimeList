package com.chs.youranimelist.domain.repository

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.domain.model.AnimeThemeInfo
import com.chs.youranimelist.domain.model.SeasonType
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.model.SortFilter
import kotlinx.coroutines.flow.Flow

interface AnimeRepository : BaseMediaRepository<AnimeInfo> {

    suspend fun getAnimeRecommendList(
        currentSeason: SeasonType,
        nextSeason: SeasonType,
        currentYear: Int,
        variationYear: Int
    ): DataResult<AnimeRecommendList, DataError.RemoteError>

    fun getAnimeFilteredList(filter: SortFilter): Flow<PagingData<AnimeInfo>>

    suspend fun getAnimeDetailInfo(animeId: Int): DataResult<AnimeDetailInfo, DataError.RemoteError>

    fun getAnimeDetailInfoRecommendList(animeId: Int): Flow<PagingData<AnimeInfo>>

    suspend fun getAnimeDetailTheme(animeId: Int): DataResult<AnimeThemeInfo, DataError.RemoteError>

    suspend fun getRecentGenreTagList()

    suspend fun getSavedGenreList(): List<String>

    suspend fun getSavedTagList(): List<Pair<String, String?>>

    override fun getSavedMediaInfoList(): Flow<List<AnimeInfo>>

    override suspend fun deleteMediaInfo(info: AnimeInfo)

    override fun getSavedMediaInfo(id: Int): Flow<AnimeInfo?>

    override suspend fun insertMediaInfo(info: AnimeInfo)
}