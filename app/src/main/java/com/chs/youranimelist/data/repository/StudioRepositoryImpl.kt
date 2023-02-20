package com.chs.youranimelist.data.repository

import androidx.paging.PagingData
import com.chs.youranimelist.StudioAnimeQuery
import com.chs.youranimelist.domain.repository.StudioRepository
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow

class StudioRepositoryImpl() : StudioRepository {
    override fun getStudioAnime(
        studioId: Int,
        sort: MediaSort
    ): Flow<PagingData<StudioAnimeQuery.Data>> {
        TODO("Not yet implemented")
    }

}