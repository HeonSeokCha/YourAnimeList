package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.GenreQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeListRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : AnimeListRepository{

    override suspend fun getAnimeList(
        page: Int,
        sort: MediaSort,
        season: MediaSeason,
        seasonYear: Int,
        genre: String
    ) = apolloClient.query(
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
        genre: String
    ) = apolloClient.query(
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
        genre: String
    ) = apolloClient.query(
        NoSeasonQuery(
            page,
            sort,
            seasonYear,
            genre
        )
    ).execute()


    override suspend fun getGenre() =
        apolloClient.query(GenreQuery()).execute()

}