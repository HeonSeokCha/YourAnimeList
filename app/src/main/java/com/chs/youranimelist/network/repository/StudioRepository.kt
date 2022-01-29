package com.chs.youranimelist.network.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.type.MediaSort
import javax.inject.Inject

class StudioRepository(
    private val apolloClient: ApolloClient
) {

    fun getStudioAnime(studioId: Int, sort: MediaSort, page: Int) =
        apolloClient.query(
            StudioAnimeQuery(
                studioId.toInput(),
                sort.toInput(),
                page.toInput()
            )
        ).toFlow()
}