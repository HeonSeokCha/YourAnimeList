package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.type.MediaSort
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudioRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : StudioRepository {

    override suspend fun getStudioAnime(
        studioId: Int,
        sort: MediaSort,
        page: Int
    ) = apolloClient.query(
        StudioAnimeQuery(studioId, sort, page)
    ).execute()
}