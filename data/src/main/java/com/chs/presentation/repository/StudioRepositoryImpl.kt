package com.chs.presentation.repository

import androidx.paging.PagingData
import com.chs.presentation.StudioAnimeQuery
import com.chs.presentation.domain.repository.StudioRepository
import com.chs.presentation.type.MediaSort
import kotlinx.coroutines.flow.Flow

class StudioRepositoryImpl() : StudioRepository {
    override fun getStudioAnime(
        studioId: Int,
        sort: MediaSort
    ): Flow<PagingData<StudioAnimeQuery.Data>> {
        TODO("Not yet implemented")
    }

}