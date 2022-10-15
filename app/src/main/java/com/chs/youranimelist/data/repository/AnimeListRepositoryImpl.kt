package com.chs.youranimelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.*
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.data.paging.AnimeNoSeasonNoYearPagingSource
import com.chs.youranimelist.data.paging.AnimeNoSeasonPagingSource
import com.chs.youranimelist.data.paging.AnimeRecPagingSource
import com.chs.youranimelist.data.paging.AnimeSortPagingSource
import com.chs.youranimelist.data.source.AnimeListDatabase
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.ConvertDate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeListRepositoryImpl @Inject constructor(
    private val apollo: ApolloClient,
    private val db: AnimeListDatabase
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

    override fun getAnimeList(
        sort: MediaSort,
        season: MediaSeason,
        seasonYear: Int,
        genre: String?
    ): Flow<PagingData<AnimeListQuery.Medium>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            AnimeSortPagingSource(
                apolloClient = apollo,
                sort = sort,
                season = season,
                seasonYear = seasonYear,
                genre = genre
            )
        }.flow
    }

    override fun getNoSeasonNoYearList(
        sort: MediaSort,
        genre: String?
    ): Flow<PagingData<NoSeasonNoYearQuery.Medium>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            AnimeNoSeasonNoYearPagingSource(
                apolloClient = apollo,
                sort = sort,
                genre = genre
            )
        }.flow
    }

    override fun getNoSeasonList(
        sort: MediaSort,
        seasonYear: Int,
        genre: String?
    ): Flow<PagingData<NoSeasonQuery.Medium>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            AnimeNoSeasonPagingSource(
                apolloClient = apollo,
                sort = sort,
                seasonYear = seasonYear,
                genre = genre
            )
        }.flow
    }

    override suspend fun getGenre() =
        apollo.query(GenreQuery()).execute()


    override fun checkAnimeList(animeId: Int): Flow<AnimeDto?> =
        db.animeListDao.checkAnimeList(animeId)

    override fun getYourAnimeList(): Flow<List<AnimeDto>> =
        db.animeListDao.getAllAnimeList()

    override suspend fun insertAnime(anime: AnimeDto) {
        db.animeListDao.insertAnimeList(anime)
    }

    override suspend fun deleteAnime(animeId: AnimeDto) {
        db.animeListDao.deleteAnimeList(animeId)
    }

    override fun searchAnimeList(animeTitle: String): Flow<List<AnimeDto>> {
        return db.animeListDao.searchAnimeList(animeTitle)
    }
}