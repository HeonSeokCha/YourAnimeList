package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.common.Resource
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeRecommendList
import com.chs.domain.model.AnimeThemeInfo
import com.chs.domain.model.DataError
import com.chs.domain.model.Result
import com.chs.domain.model.SortFilter
import com.chs.domain.model.TagInfo
import kotlinx.coroutines.flow.Flow

interface AnimeRepository : BaseMediaRepository<AnimeInfo> {

    suspend fun getAnimeRecommendList(
        currentSeason: String,
        nextSeason: String,
        currentYear: Int,
        variationYear: Int
    ): Result<AnimeRecommendList, DataError.RemoteError>

    fun getAnimeFilteredList(filter: SortFilter): Flow<PagingData<AnimeInfo>>

    fun getAnimeDetailInfo(animeId: Int): Flow<Resource<AnimeDetailInfo>>

    fun getAnimeDetailInfoRecommendList(animeId: Int): Flow<PagingData<AnimeInfo>>

    fun getAnimeDetailTheme(animeId: Int): Flow<Resource<AnimeThemeInfo>>

    suspend fun getRecentGenreTagList()

    suspend fun getSavedGenreList(): List<String>

    suspend fun getSavedTagList(): List<Pair<String, String?>>

    override fun getSavedMediaInfoList(): Flow<List<AnimeInfo>>

    override suspend fun deleteMediaInfo(info: AnimeInfo)

    override fun getSavedMediaInfo(id: Int): Flow<AnimeInfo?>

    override suspend fun insertMediaInfo(info: AnimeInfo)
}