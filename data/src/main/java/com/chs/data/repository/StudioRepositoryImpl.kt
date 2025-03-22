package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.data.StudioQuery
import com.chs.common.Constants
import com.chs.data.mapper.toStudioDetailInfo
import com.chs.data.paging.StudioAnimePagingSource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.repository.StudioRepository
import com.chs.data.type.MediaSort
import com.chs.common.DataError
import com.chs.common.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudioRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : StudioRepository {
    override suspend fun getStudioDetailInfo(studioId: Int): DataResult<StudioDetailInfo, DataError.RemoteError> {
        return try {
            val response = apolloClient
                .query(StudioQuery(id = Optional.present(studioId)))
                .execute()
            if (response.data == null) {
                return if (response.exception == null) {
                    DataResult.Error(DataError.RemoteError(response.errors!!.first().message))
                } else {
                    DataResult.Error(DataError.RemoteError(response.exception!!.message))
                }
            }
            DataResult.Success(response.data?.toStudioDetailInfo()!!)
        } catch (e: Exception) {
            DataResult.Error(DataError.RemoteError(e.message))
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