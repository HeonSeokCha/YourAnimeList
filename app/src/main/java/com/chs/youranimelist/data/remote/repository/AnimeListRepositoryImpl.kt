package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
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

    override fun getAnimeList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: Input<MediaSeason>,
        seasonYear: Input<Int>,
        genre: Input<String>
    ) = apolloClient.query(
        AnimeListQuery(
            page,
            sort,
            season,
            seasonYear,
            genre
        )
    ).toFlow()

    override fun getNoSeasonNoYearList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        genre: Input<String>
    ) = apolloClient.query(
        NoSeasonNoYearQuery(
            page,
            sort,
            genre
        )
    ).toFlow()


    override fun getNoSeasonList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        seasonYear: Input<Int>,
        genre: Input<String>
    ) = apolloClient.query(
        NoSeasonQuery(
            page,
            sort,
            seasonYear,
            genre
        )
    ).toFlow()


    override fun getGenre() =
        apolloClient.query(GenreQuery()).toFlow()

}