package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.common.Constants
import com.chs.common.Resource
import com.chs.data.ActorDetailQuery
import com.chs.data.mapper.toVoiceActorDetailInfo
import com.chs.data.paging.VoiceActorAnimePagingSource
import com.chs.data.type.MediaSort
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.model.DataError
import com.chs.domain.model.DataResult
import com.chs.domain.model.VoiceActorDetailInfo
import com.chs.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActorRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : ActorRepository {

    override suspend fun getActorDetailInfo(actorId: Int): DataResult<VoiceActorDetailInfo, DataError.RemoteError> {
        return try {
            val response = apolloClient
                .query(ActorDetailQuery(Optional.present(actorId)))
                .execute()

            if (response.data == null) {
                return if (response.exception == null) {
                    DataResult.Error(DataError.RemoteError(response.errors!!.first().message))
                } else {
                    DataResult.Error(DataError.RemoteError(response.exception!!.message))
                }
            }

            DataResult.Success(response.data.toVoiceActorDetailInfo())
        } catch (e: Exception) {
            DataResult.Error(DataError.RemoteError(e.message))
        }
    }

    override fun getActorRelationAnimeList(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                initialLoadSize = Constants.PAGING_SIZE * 3
            )
        ) {
            VoiceActorAnimePagingSource(
                apolloClient = apolloClient,
                voiceActorId = actorId,
                sortOptions = sortOptions.map { MediaSort.safeValueOf(it) }
            )
        }.flow
    }

}