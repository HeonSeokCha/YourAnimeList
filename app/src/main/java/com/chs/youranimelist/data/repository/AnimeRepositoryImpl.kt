package com.chs.youranimelist.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.AnimeDetailInfoQuery
import com.chs.AnimeRecommendQuery
import com.chs.HomeAnimeListQuery
import com.chs.SearchAnimeQuery
import com.chs.youranimelist.data.ConvertDate
import com.chs.youranimelist.data.mapper.*
import com.chs.youranimelist.data.source.KtorJikanService
import com.chs.youranimelist.domain.model.*
import com.chs.youranimelist.domain.repository.AnimeRepository

class AnimeRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val jikanService: KtorJikanService
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

    override suspend fun getSavedAnimeList(): List<AnimeInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedAnimeInfo(): AnimeInfo? {
        TODO("Not yet implemented")
    }

    override suspend fun insertSavedAnimeInfo(animeInfo: AnimeInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSavedAnimeInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeGenreList() {
        TODO("Not yet implemented")
    }
}