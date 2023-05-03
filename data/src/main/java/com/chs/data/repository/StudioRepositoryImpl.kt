package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.StudioQuery
import com.chs.data.mapper.toStudioDetailInfo
import com.chs.data.paging.StudioAnimePagingSource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.repository.StudioRepository
import com.chs.type.MediaSort
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudioRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : StudioRepository {
    override suspend fun getStudioDetailInfo(studioId: Int): StudioDetailInfo {
        return apolloClient
            .query(
                StudioQuery(id = Optional.present(studioId))
            )
            .execute()
            .data
            ?.toStudioDetailInfo()!!
    }

    override fun getStudioAnimeList(
        studioId: Int,
        studioSort: String
    ): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            StudioAnimePagingSource(
                apolloClient,
                studioId = studioId,
                sort = MediaSort.valueOf(studioSort)
            )
        }.flow
    }
}