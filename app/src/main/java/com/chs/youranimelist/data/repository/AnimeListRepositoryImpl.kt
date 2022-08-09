package com.chs.youranimelist.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.*
import com.chs.youranimelist.data.model.AnimeDto
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

    override suspend fun getAnimeList(
        page: Int,
        sort: MediaSort,
        season: MediaSeason,
        seasonYear: Int,
        genre: String?
    ) = apollo.query(
        AnimeListQuery(
            page,
            sort,
            season,
            seasonYear,
            genre
        )
    ).execute()

    override suspend fun getNoSeasonNoYearList(
        page: Int,
        sort: MediaSort,
        genre: String?
    ) = apollo.query(
        NoSeasonNoYearQuery(
            page,
            sort,
            genre
        )
    ).execute()


    override suspend fun getNoSeasonList(
        page: Int,
        sort: MediaSort,
        seasonYear: Int,
        genre: String?
    ) = apollo.query(
        NoSeasonQuery(
            page,
            sort,
            seasonYear,
            genre
        )
    ).execute()


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