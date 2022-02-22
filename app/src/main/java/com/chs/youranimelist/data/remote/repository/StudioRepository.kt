package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow

interface StudioRepository {

    suspend fun getStudioAnime(
        studioId: Input<Int>,
        sort: Input<MediaSort>,
        page: Input<Int>
    ): Response<StudioAnimeQuery.Data>

}