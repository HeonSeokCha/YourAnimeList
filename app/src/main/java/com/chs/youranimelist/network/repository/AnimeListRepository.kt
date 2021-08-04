package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.GenreQuery
import com.chs.youranimelist.network.services.ApolloServices
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.type.MediaStatus

class AnimeListRepository {

    fun getAnimeList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: Input<MediaSeason>,
        seasonYear: Input<Int>,
        status: Input<MediaStatus>,
        genre: Input<String>
    ) = ApolloServices.apolloClient.query(
        AnimeListQuery(
            page,
            sort,
            season,
            seasonYear,
            status,
            genre
        )
    ).toFlow()


    fun getGenre() =
        ApolloServices.apolloClient.query(GenreQuery()).toFlow()

}