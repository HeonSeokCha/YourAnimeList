package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.common.Constants
import com.chs.common.Resource
import com.chs.data.AnimeDetailInfoQuery
import com.chs.data.GenreQuery
import com.chs.data.HomeAnimeListQuery
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
import com.chs.data.type.MediaSeason
import com.chs.data.type.MediaSort
import com.chs.data.type.MediaStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val jikanService: KtorJikanService,
    private val animeDao: AnimeListDao,
    private val genreDao: GenreDao
) : AnimeRepository {

    override fun getAnimeRecommendList(
        currentSeason: String,
        nextSeason: String,
        currentYear: Int,
        nextYear: Int
    ): Flow<Resource<AnimeRecommendList>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apolloClient
                    .query(
                        HomeAnimeListQuery(
                            currentSeason = Optional.present(MediaSeason.valueOf(currentSeason)),
                            nextSeason = Optional.present(MediaSeason.valueOf(nextSeason)),
                            currentYear = Optional.present(currentYear),
                            nextYear = Optional.present(nextYear)
                        )
                    )
                    .execute()

                if (response.data == null) {
                    return@flow if (response.exception == null) {
                        emit(Resource.Error(response.errors!!.first().message))
                    } else {
                        emit(Resource.Error(response.exception!!.message))
                    }
                }

                emit(Resource.Success(response.data.toAnimeRecommendList()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override fun getAnimeFilteredList(
        sortType: List<String>,
        season: String?,
        year: Int?,
        genre: String?,
        status: String?
    ): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                initialLoadSize = Constants.PAGING_SIZE * 3
            )
        ) {
            AnimeSortPagingSource(
                apolloClient = apolloClient,
                sort = sortType.map { MediaSort.safeValueOf(it) },
                season = if (season != null) MediaSeason.safeValueOf(season) else null,
                seasonYear = year,
                genre = genre,
                status = if (status != null) MediaStatus.safeValueOf(status) else null
            )
        }.flow
    }

    override fun getAnimeDetailInfo(animeId: Int): Flow<Resource<AnimeDetailInfo>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apolloClient
                    .query(
                        AnimeDetailInfoQuery(Optional.present(animeId))
                    )
                    .execute()

                if (response.data == null) {
                    return@flow if (response.exception == null) {
                        emit(Resource.Error(response.errors!!.first().message))
                    } else {
                        emit(Resource.Error(response.exception!!.message))
                    }
                }

                emit(Resource.Success(response.data!!.toAnimeDetailInfo()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
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

    override fun getAnimeDetailTheme(animeId: Int): Flow<Resource<AnimeThemeInfo>> {
        return flow {
            emit(Resource.Loading())
            try {
                emit(
                    Resource.Success(
                        jikanService.getAnimeTheme(animeId).toAnimeThemeInfo()
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
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

    override suspend fun getRecentGenreList() {
        val genreList: List<String> = try {
            apolloClient
                .query(GenreQuery())
                .execute()
                .data
                ?.genreCollection
                ?.filterNotNull() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        if (genreList.isNotEmpty()) {
            genreDao.insertMultiple(
                *genreList.map {
                    GenreEntity(it)
                }.toTypedArray()
            )
        }
    }

    override suspend fun getSavedGenreList(): List<String> {
        return genreDao.getAllGenres().map {
            it.name
        }
    }
}