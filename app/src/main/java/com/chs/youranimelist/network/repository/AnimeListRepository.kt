package com.chs.youranimelist.network.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.GenreQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort

class AnimeListRepository(
    private val apolloClient: ApolloClient
) {

    fun getAnimeList(
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

    fun getNoSeasonNoYearList(
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


    fun getNoSeasonList(
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


    fun getGenre() =
        apolloClient.query(GenreQuery()).toFlow()

}