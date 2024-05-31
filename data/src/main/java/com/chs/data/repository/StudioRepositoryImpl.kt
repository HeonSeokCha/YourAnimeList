package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.data.StudioQuery
import com.chs.common.Constants
import com.chs.data.mapper.toStudioDetailInfo
import com.chs.data.paging.StudioAnimePagingSource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.repository.StudioRepository
import com.chs.data.type.MediaSort
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudioRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : StudioRepository {
    override suspend fun getStudioDetailInfo(studioId: Int): StudioDetailInfo {
        return try {
            apolloClient
                .query(
                    StudioQuery(id = Optional.present(studioId))
                )
                .execute()
                .data
                ?.toStudioDetailInfo()!!
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getStudioAnimeList(
        studioId: Int,
        studioSort: String
    ): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                initialLoadSize = Constants.PAGING_SIZE * 3
            )
        ) {
            StudioAnimePagingSource(
                apolloClient,
                studioId = studioId,
                sort = MediaSort.valueOf(studioSort)
            )
        }.flow
    }
}