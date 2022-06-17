package com.chs.youranimelist.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.*
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.data.source.AnimeListDao
import com.chs.youranimelist.domain.model.Anime
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.ConvertDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimeListRepositoryImpl(
    private val apollo: ApolloClient,
    private val dao: AnimeListDao
) : AnimeListRepository {
    override suspend fun getHomeRecommendList(): ApolloResponse<HomeRecommendListQuery.Data> {
        return apollo.query(
            HomeRecommendListQuery(
                ConvertDate.getCurrentSeason(),
                ConvertDate.getNextSeason(),
                ConvertDate.getCurrentYear(false),
                ConvertDate.getCurrentYear(true)
            )
        ).execute()
    }


    override fun getAnimeLists(): Flow<List<AnimeDto>> = dao.getAllAnimeList()


    override suspend fun insertAnime(anime: AnimeDto) {
        dao.insertAnimeList(anime)
    }

    override suspend fun deleteAnime(anime: AnimeDto) {
        dao.deleteAnimeList(anime)
    }
}