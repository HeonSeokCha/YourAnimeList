package com.chs.youranimelist.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.data.model.AnimeDetails
import com.chs.youranimelist.data.datasource.JikanService
import com.chs.youranimelist.domain.repository.AnimeRepository
import com.chs.youranimelist.home.HomeRecommendListQuery
import com.chs.youranimelist.util.ConvertDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val jikanClient: JikanService
) : AnimeRepository {

    override suspend fun getHomeRecommendList(): ApolloResponse<HomeRecommendListQuery.Data> {
        return apolloClient.query(
            HomeRecommendListQuery(
                ConvertDate.getCurrentSeason(),
                ConvertDate.getNextSeason(),
                ConvertDate.getCurrentYear(false),
                ConvertDate.getCurrentYear(true)
            )
        ).execute()
    }

    override suspend fun getAnimeDetail(
        animeId: Int
    ): ApolloResponse<AnimeDetailQuery.Data> {
        return apolloClient.query(AnimeDetailQuery(animeId)).execute()
    }

    override suspend fun getAnimeRecList(
        animeId: Int,
        page: Int
    ): ApolloResponse<AnimeRecommendQuery.Data> {
        return apolloClient.query(AnimeRecommendQuery(animeId, page)).execute()
    }

    override suspend fun getAnimeOverviewTheme(animeId: Int): AnimeDetails? {
        return jikanClient.getAnimeTheme(animeId)
    }
}