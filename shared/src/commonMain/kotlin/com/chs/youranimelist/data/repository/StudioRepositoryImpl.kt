package com.chs.youranimelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.youranimelist.data.StudioQuery
import com.chs.youranimelist.util.Constants
import com.chs.youranimelist.data.mapper.toStudioDetailInfo
import com.chs.youranimelist.data.paging.StudioAnimePagingSource
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.StudioDetailInfo
import com.chs.youranimelist.domain.repository.StudioRepository
import com.chs.youranimelist.data.type.MediaSort
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import kotlinx.coroutines.flow.Flow

class StudioRepositoryImpl(
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