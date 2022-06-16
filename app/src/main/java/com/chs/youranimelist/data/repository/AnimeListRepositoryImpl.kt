package com.chs.youranimelist.data.repository

import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.GenreQuery
import com.chs.youranimelist.NoSeasonNoYearQuery
import com.chs.youranimelist.NoSeasonQuery
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.data.source.AnimeListDao
import com.chs.youranimelist.domain.model.Anime
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimeListRepositoryImpl(
//    private val apollo: ApolloClient,
    private val dao: AnimeListDao
) : AnimeListRepository {

//    override suspend fun getAnimeRecommendList(
//        page: Int,
//        sort: MediaSort,
//        season: MediaSeason,
//        seasonYear: Int,
//        genre: String?
//    ) = apollo.query(
//        AnimeListQuery(
//            page,
//            sort,
//            season,
//            seasonYear,
//            genre
//        )
//    ).toFlow()
//
//    override suspend fun getNoSeasonNoYearList(
//        page: Int,
//        sort: MediaSort,
//        genre: String?
//    ) = apollo.query(
//        NoSeasonNoYearQuery(
//            page,
//            sort,
//            genre
//        )
//    ).toFlow()
//
//
//    override suspend fun getNoSeasonList(
//        page: Int,
//        sort: MediaSort,
//        seasonYear: Int,
//        genre: String?
//    ) = apollo.query(
//        NoSeasonQuery(
//            page,
//            sort,
//            seasonYear,
//            genre
//        )
//    ).toFlow()
//
//
//    override suspend fun getGenre() = apollo.query(GenreQuery()).toFlow()

    override fun getAnimeLists(): Flow<List<AnimeDto>> = dao.getAllAnimeList()


    override suspend fun insertAnime(anime: AnimeDto) {
        dao.insertAnimeList(anime)
    }

    override suspend fun deleteAnime(anime: AnimeDto) {
        dao.deleteAnimeList(anime)
    }
}