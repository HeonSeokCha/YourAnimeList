package com.chs.youranimelist.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.type.MediaSort

interface StudioRepository {

    suspend fun getStudioAnime(
        studioId: Int,
        sort: MediaSort,
        page: Int
    ): ApolloResponse<StudioAnimeQuery.Data>

}