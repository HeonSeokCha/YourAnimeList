package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.data.StudioQuery
import com.chs.common.Constants
import com.chs.common.Resource
import com.chs.data.mapper.toStudioDetailInfo
import com.chs.data.paging.StudioAnimePagingSource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.repository.StudioRepository
import com.chs.data.type.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StudioRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : StudioRepository {
    override fun getStudioDetailInfo(studioId: Int): Flow<Resource<StudioDetailInfo>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apolloClient
                    .query(StudioQuery(id = Optional.present(studioId)))
                    .execute()

                if (response.data == null) {
                    return@flow if (response.exception == null) {
                        emit(Resource.Error(response.errors!!.first().message))
                    } else {
                        emit(Resource.Error(response.exception!!.message))
                    }
                }

                emit(Resource.Success(response.data?.toStudioDetailInfo()!!))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
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