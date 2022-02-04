package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.dto.AnimeDetails
import com.chs.youranimelist.data.remote.services.JikanService
import com.chs.youranimelist.home.HomeRecommendListQuery
import com.chs.youranimelist.util.ConvertDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
//    private val jikanClient: JikanService
) : AnimeRepository {

    override fun getHomeRecommendList(): Flow<Response<HomeRecommendListQuery.Data>> {
        return apolloClient.query(
            HomeRecommendListQuery(
                ConvertDate.getCurrentSeason().toInput(),
                ConvertDate.getNextSeason().toInput(),
                ConvertDate.getCurrentYear(false).toInput(),
                ConvertDate.getCurrentYear(true).toInput()
            )
        ).toFlow().flowOn(Dispatchers.IO)
    }

    override fun getAnimeDetail(animeId: Input<Int>): Flow<Response<AnimeDetailQuery.Data>> {
        return apolloClient.query(AnimeDetailQuery(animeId)).toFlow()
            .flowOn(Dispatchers.IO)
    }

    override fun getAnimeOverview(animeId: Input<Int>): Flow<Response<AnimeOverviewQuery.Data>> {
        return apolloClient.query(AnimeOverviewQuery(animeId)).toFlow()
            .flowOn(Dispatchers.IO)
    }

    override fun getAnimeCharacter(animeId: Input<Int>): Flow<Response<AnimeCharacterQuery.Data>> {
        return apolloClient.query(AnimeCharacterQuery(animeId)).toFlow()
            .flowOn(Dispatchers.IO)
    }

    override fun getAnimeRecList(
        animeId: Input<Int>,
        page: Input<Int>
    ): Flow<Response<AnimeRecommendQuery.Data>> {
        return apolloClient.query(AnimeRecommendQuery(animeId, page)).toFlow()
            .flowOn(Dispatchers.IO)
    }

//    override suspend fun getAnimeOverviewTheme(animeId: Int): NetWorkState<AnimeDetails> {
//        return jikanClient.getAnimeTheme(animeId)
//    }
}