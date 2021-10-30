package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.StudioAnimeQuery
import com.chs.youranimelist.network.services.ApolloServices
import com.chs.youranimelist.type.MediaSort

class StudioRepository {

    fun getStudioAnime(studioId: Int, sort: MediaSort, page: Int) =
        ApolloServices.apolloClient.query(
            StudioAnimeQuery(
                studioId.toInput(),
                sort.toInput(),
                page.toInput()
            )
        ).toFlow()
}