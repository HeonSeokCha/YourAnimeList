package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.network.services.ApolloServices
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.GenreQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort

class AnimeListRepository {

    fun getAnimeList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: Input<MediaSeason>,
        seasonYear: Input<Int>,
        genre: Input<String>
    ) = ApolloServices.apolloClient.query(
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
    ) = ApolloServices.apolloClient.query(
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
    ) = ApolloServices.apolloClient.query(
        NoSeasonQuery(
            page,
            sort,
            seasonYear,
            genre
        )
    ).toFlow()


    fun getGenre() =
        ApolloServices.apolloClient.query(GenreQuery()).toFlow()

}