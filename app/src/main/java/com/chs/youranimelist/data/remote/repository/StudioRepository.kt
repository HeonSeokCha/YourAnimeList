package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.api.Response
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow

interface StudioRepository {

    fun getStudioAnime(
        studioId: Int,
        sort: MediaSort,
        page: Int
    ): Flow<Response<StudioAnimeQuery.Data>>

}