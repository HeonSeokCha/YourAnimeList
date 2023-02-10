package com.chs.youranimelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.*
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.data.paging.AnimeSortPagingSource
import com.chs.youranimelist.data.source.AnimeListDatabase
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.fragment.AnimeList
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

    override fun getSortedAnimeList(
        selectType: String,
        sort: MediaSort,
        season: MediaSeason?,
        seasonYear: Int?,
        genre: String?
    ): Flow<PagingData<AnimeList>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            AnimeSortPagingSource(
                apolloClient = apollo,
                selectType = selectType,
                sort = sort,
                season = season,
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
        db.animeListDao.insert(anime)
    }

    override suspend fun deleteAnime(animeId: AnimeDto) {
        db.animeListDao.delete(animeId)
    }

    override fun searchAnimeList(animeTitle: String): Flow<List<AnimeDto>> {
        return db.animeListDao.searchAnimeList(animeTitle)
    }
}