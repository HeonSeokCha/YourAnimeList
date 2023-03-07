package com.chs.youranimelist.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.*
import com.chs.youranimelist.ConvertDate
import com.chs.youranimelist.data.mapper.*
import com.chs.youranimelist.source.KtorJikanService
import com.chs.youranimelist.source.db.dao.AnimeListDao
import com.chs.youranimelist.domain.model.*
import com.chs.youranimelist.domain.repository.AnimeRepository
import com.chs.youranimelist.mapper.*
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
    ): ListInfo<AnimeInfo> {
        TODO("Not yet implemented")
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

    override suspend fun getAnimeDetailInfoRecommendList(
        page: Int,
        animeId: Int
    ): ListInfo<AnimeInfo> {
        return apolloClient
            .query(
                AnimeRecommendQuery(id = Optional.present(animeId), page = Optional.present(page))
            )
            .execute()
            .data
            ?.toAnimeInfoList()!!
    }

    override suspend fun getAnimeDetailTheme(animeId: Int): AnimeThemeInfo {
        return jikanService.getAnimeTheme(animeId)?.toAnimeThemeInfo()!!
    }

    override suspend fun getAnimeSearchResult(
        page: Int,
        query: String
    ): ListInfo<AnimeInfo> {
        return apolloClient
            .query(
                SearchAnimeQuery(
                    page = Optional.present(page),
                    search = Optional.present(query)
                )
            )
            .execute()
            .data
            ?.toAnimeList()!!
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
            .data?.genreCollection?.map {
                it.let { it!! }
            } ?: emptyList()
    }
}