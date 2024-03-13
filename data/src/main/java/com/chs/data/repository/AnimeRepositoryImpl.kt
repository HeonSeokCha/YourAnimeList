package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.*
import com.chs.common.Constants
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeRecommendList
import com.chs.domain.model.AnimeThemeInfo
import com.chs.domain.repository.AnimeRepository
import com.chs.data.source.KtorJikanService
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.mapper.*
import com.chs.data.paging.AnimeRecPagingSource
import com.chs.data.paging.AnimeSortPagingSource
import com.chs.data.source.db.dao.GenreDao
import com.chs.data.source.db.entity.GenreEntity
import com.chs.type.MediaSeason
import com.chs.type.MediaSort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class AnimeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val jikanService: KtorJikanService,
    private val animeDao: AnimeListDao,
    private val genreDao: GenreDao
) : AnimeRepository {
    override suspend fun getAnimeRecommendList(
        currentSeason: String,
        nextSeason: String,
        currentYear: Int,
        lastYear: Int,
        nextYear: Int
    ): AnimeRecommendList {
        return apolloClient
            .query(
                HomeAnimeListQuery(
                    currentSeason = Optional.present(MediaSeason.valueOf(currentSeason)),
                    nextSeason = Optional.present(MediaSeason.valueOf(nextSeason)),
                    currentYear = Optional.present(currentYear),
                    nextYear = Optional.present(nextYear),
                    lastYear = Optional.present(lastYear)
                )
            )
            .execute()
            .data
            ?.toAnimeRecommendList()!!
    }

    override fun getAnimeFilteredList(
        sortType: List<String>,
        season: String?,
        year: Int?,
        genre: String?
    ): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            AnimeSortPagingSource(
                apolloClient = apolloClient,
                sort = sortType.map { MediaSort.safeValueOf(it) },
                season = if (season != null) MediaSeason.safeValueOf(season) else null,
                seasonYear = year,
                genre = genre
            )
        }.flow
    }

    override suspend fun getAnimeDetailInfo(animeId: Int): AnimeDetailInfo {
        return apolloClient
            .query(
                AnimeDetailInfoQuery(id = Optional.present(animeId))
            )
            .execute()
            .data
            ?.toAnimeDetailInfo()!!
    }

    override fun getAnimeDetailInfoRecommendList(animeId: Int): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            AnimeRecPagingSource(
                apolloClient = apolloClient,
                animeId = animeId
            )
        }.flow
    }

    override suspend fun getAnimeDetailTheme(animeId: Int): AnimeThemeInfo {
        return jikanService.getAnimeTheme(animeId).toAnimeThemeInfo()
    }


    override fun getSavedAnimeList(): Flow<List<AnimeInfo>> {
        return animeDao.getAllAnimeList().map {
            it.map { animeEntity ->
                animeEntity.toAnimeInfo()
            }
        }
    }

    override fun getSavedAnimeInfo(id: Int): Flow<AnimeInfo?> {
        return animeDao.checkAnimeList(id).map {
            it?.toAnimeInfo()
        }
    }

    override suspend fun insertSavedAnimeInfo(animeInfo: AnimeInfo) {
        animeDao.insert(animeInfo.toAnimeEntity())
    }

    override suspend fun deleteSavedAnimeInfo(animeInfo: AnimeInfo) {
        animeDao.delete(animeInfo.toAnimeEntity())
    }

    override suspend fun getRecentGenreList() {
        val genreList: List<String> = withContext(Dispatchers.IO) {
            apolloClient
                .query(GenreQuery())
                .execute()
                .data
                ?.genreCollection
                ?.filterNotNull() ?: emptyList()
        }

        genreDao.insertMultiple(
            *genreList.map {
                GenreEntity(it)
            }.toTypedArray()
        )
    }

    override suspend fun getSavedGenreList(): List<String> {
        return genreDao.getAllGenres().map {
            it.name
        }
    }
}