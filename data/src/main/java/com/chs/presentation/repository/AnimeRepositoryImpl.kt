package com.chs.presentation.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.*
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeRecommendList
import com.chs.domain.model.AnimeThemeInfo
import com.chs.domain.repository.AnimeRepository
import com.chs.presentation.ConvertDate
import com.chs.presentation.source.KtorJikanService
import com.chs.presentation.source.db.dao.AnimeListDao
import com.chs.presentation.mapper.*
import com.chs.presentation.paging.AnimeRecPagingSource
import com.chs.presentation.paging.AnimeSortPagingSource
import com.chs.presentation.paging.SearchAnimePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimeRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val jikanService: KtorJikanService,
    private val dao: AnimeListDao
) : AnimeRepository {
    override suspend fun getAnimeRecommendList(): AnimeRecommendList {
        return apolloClient
            .query(
                HomeAnimeListQuery(
                    currentSeason = Optional.present(ConvertDate.getCurrentSeason()),
                    nextSeason = Optional.present(ConvertDate.getNextSeason()),
                    currentYear = Optional.present(ConvertDate.getCurrentYear()),
                    nextYear = Optional.present(ConvertDate.getCurrentYear() + 1),
                    lastYear = Optional.present(ConvertDate.getCurrentYear() - 1)
                )
            )
            .execute()
            .data
            ?.toAnimeRecommendList()!!
    }

    override suspend fun getAnimeFilteredList(
        selectType: String,
        sortType: String,
        season: String,
        year: Int,
        genre: String?
    ): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            AnimeSortPagingSource(apolloClient)
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

    override suspend fun getAnimeDetailInfoRecommendList(animeId: Int): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            AnimeRecPagingSource(
                apolloClient = apolloClient,
                animeId = animeId
            )
        }.flow
    }

    override suspend fun getAnimeDetailTheme(animeId: Int): AnimeThemeInfo {
        return jikanService.getAnimeTheme(animeId)?.toAnimeThemeInfo()!!
    }

    override suspend fun getAnimeSearchResult(query: String): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchAnimePagingSource(
                apolloClient = apolloClient,
                search = query
            )
        }.flow
    }

    override fun getSavedAnimeList(): Flow<List<AnimeInfo>> {
        return dao.getAllAnimeList().map {
            it.map { animeEntity ->
                animeEntity.toAnimeInfo()
            }
        }
    }

    override fun getSavedAnimeInfo(id: Int): Flow<AnimeInfo?> {
        return dao.checkAnimeList(id).map {
            it?.toAnimeInfo()
        }
    }

    override suspend fun insertSavedAnimeInfo(animeInfo: AnimeInfo) {
        dao.insert(animeInfo.toAnimeEntity())
    }

    override suspend fun deleteSavedAnimeInfo(animeInfo: AnimeInfo) {
        dao.delete(animeInfo.toAnimeEntity())
    }

    override suspend fun getAnimeGenreList(): List<String> {
        return apolloClient.query(
            GenreQuery()
        )
            .execute()
            .data
            ?.genreCollection
            ?.map {
                it.let { it!! }
            } ?: emptyList()
    }
}