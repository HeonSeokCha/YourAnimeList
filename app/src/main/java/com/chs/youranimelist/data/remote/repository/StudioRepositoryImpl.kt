package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudioRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : StudioRepository {

    override suspend fun getStudioAnime(
        studioId: Input<Int>,
        sort: Input<MediaSort>,
        page: Input<Int>
    ) = apolloClient.query(
        StudioAnimeQuery(studioId, sort, page)
    ).await()
}