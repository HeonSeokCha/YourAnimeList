package com.chs.youranimelist.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.youranimelist.util.Constants
import com.chs.youranimelist.data.AnimeDetailInfoQuery
import com.chs.youranimelist.data.GenreTagQuery
import com.chs.youranimelist.data.HomeAnimeListQuery
import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.domain.model.AnimeThemeInfo
import com.chs.youranimelist.domain.repository.AnimeRepository
import com.chs.youranimelist.data.source.JikanService
import com.chs.youranimelist.data.source.db.dao.AnimeListDao
import com.chs.youranimelist.data.mapper.*
import com.chs.youranimelist.data.paging.AnimeRecPagingSource
import com.chs.youranimelist.data.paging.AnimeSortPagingSource
import com.chs.youranimelist.data.source.db.dao.GenreDao
import com.chs.youranimelist.data.source.db.dao.TagDao
import com.chs.youranimelist.data.source.db.entity.GenreEntity
import com.chs.youranimelist.data.source.db.entity.TagEntity
import com.chs.youranimelist.data.type.MediaSeason
import com.chs.youranimelist.data.type.MediaSort
import com.chs.youranimelist.data.type.MediaStatus
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.model.SortFilter
import com.chs.youranimelist.data.mapper.toAnimeDetailInfo
import com.chs.youranimelist.data.mapper.toAnimeEntity
import com.chs.youranimelist.data.mapper.toAnimeInfo
import com.chs.youranimelist.data.mapper.toAnimeRecommendList
import com.chs.youranimelist.data.mapper.toAnimeThemeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AnimeRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val jikanService: JikanService,
    private val animeDao: AnimeListDao,
    private val genreDao: GenreDao,
    private val tagDao: TagDao
) : AnimeRepository {

    override suspend fun getAnimeRecommendList(
        currentSeason: String,
        nextSeason: String,
        currentYear: Int,
        variationYear: Int
    ): DataResult<AnimeRecommendList, DataError.RemoteError> {
        return try {
            val response = apolloClient
                .query(
                    HomeAnimeListQuery(
                        currentSeason = Optional.present(MediaSeason.valueOf(currentSeason)),
                        nextSeason = Optional.present(MediaSeason.valueOf(nextSeason)),
                        currentYear = Optional.present(currentYear),
                        variationYear = Optional.present(variationYear)
                    )
                )
                .execute()

            if (response.data == null) {
                return if (response.exception == null) {
                    DataResult.Error(DataError.RemoteError(response.errors!!.first().message))
                } else {
                    DataResult.Error(DataError.RemoteError(response.exception!!.message))
                }
            }

            DataResult.Success(response.data.toAnimeRecommendList())
        } catch (e: Exception) {
            DataResult.Error(DataError.RemoteError(e.message))
        }
    }

    override fun getAnimeFilteredList(
        filter: SortFilter
    ): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                initialLoadSize = Constants.PAGING_SIZE * 3
            )
        ) {
            AnimeSortPagingSource(
                apolloClient = apolloClient,
                sort = filter.selectSort.map { MediaSort.safeValueOf(it) },
                season = MediaSeason.entries.find { it.rawValue == filter.selectSeason },
                seasonYear = filter.selectYear,
                genres = filter.selectGenre,
                tags = filter.selectTags,
                status = MediaStatus.entries.find { it.rawValue == filter.selectStatus }
            )
        }.flow
    }

    override suspend fun getAnimeDetailInfo(animeId: Int): DataResult<AnimeDetailInfo, DataError.RemoteError> {
        return try {
            val response = apolloClient
                .query(
                    AnimeDetailInfoQuery(Optional.present(animeId))
                )
                .execute()

            if (response.data == null) {
                return if (response.exception == null) {
                    DataResult.Error(DataError.RemoteError(response.errors!!.first().message))
                } else {
                    DataResult.Error(DataError.RemoteError(response.exception!!.message))
                }
            }

            DataResult.Success(response.data!!.toAnimeDetailInfo())
        } catch (e: Exception) {
            DataResult.Error(DataError.RemoteError(e.message))
        }
    }

    override fun getAnimeDetailInfoRecommendList(animeId: Int): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                initialLoadSize = Constants.PAGING_SIZE * 3
            )
        ) {
            AnimeRecPagingSource(
                apolloClient = apolloClient,
                animeId = animeId
            )
        }.flow
    }

    override suspend fun getAnimeDetailTheme(animeId: Int): DataResult<AnimeThemeInfo, DataError.RemoteError> {
        return try {
            DataResult.Success(jikanService.getAnimeTheme(animeId).toAnimeThemeInfo())
        } catch (e: Exception) {
            DataResult.Error(DataError.RemoteError(e.message))
        }
    }


    override fun getSavedMediaInfoList(): Flow<List<AnimeInfo>> {
        return animeDao.getAllAnimeList().map {
            it.map { animeEntity ->
                animeEntity.toAnimeInfo()
            }
        }
    }

    override fun getSavedMediaInfo(id: Int): Flow<AnimeInfo?> {
        return animeDao.checkAnimeList(id).map {
            it?.toAnimeInfo()
        }
    }

    override suspend fun insertMediaInfo(info: AnimeInfo) {
        animeDao.insert(info.toAnimeEntity())
    }

    override suspend fun deleteMediaInfo(info: AnimeInfo) {
        animeDao.delete(info.toAnimeEntity())
    }

    override suspend fun getRecentGenreTagList() {
        val data = apolloClient
            .query(GenreTagQuery())
            .execute()
            .data

        withContext(Dispatchers.IO) {
            val genreJob = async {
                val list = data?.genreCollection
                    ?.filterNotNull()
                    ?: emptyList()

                if (list.isEmpty()) return@async

                genreDao.insertMultiple(
                    *list.map {
                        GenreEntity(it)
                    }.toTypedArray()
                )
            }

            val tagJob = async {
                val list = data?.mediaTagCollection
                    ?.filterNotNull()
                    ?.filter { it.isAdult == false }
                    ?.map {
                        it.name to it.description
                    } ?: emptyList()

                if (list.isEmpty()) return@async

                tagDao.insertMultiple(
                    *list.map {
                        TagEntity(
                            name = it.first,
                            desc = it.second
                        )
                    }.toTypedArray()
                )
            }

            awaitAll(genreJob, tagJob)
        }
    }

    override suspend fun getSavedGenreList(): List<String> {
        return genreDao.getAllGenres().map {
            it.name
        }
    }

    override suspend fun getSavedTagList(): List<Pair<String, String?>> {
        return tagDao.getAllTags().map {
            it.name to it.desc
        }
    }
}