package com.chs.youranimelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.youranimelist.util.Constants
import com.chs.youranimelist.data.ActorDetailQuery
import com.chs.youranimelist.data.mapper.toVoiceActorDetailInfo
import com.chs.youranimelist.data.paging.VoiceActorAnimePagingSource
import com.chs.youranimelist.data.type.MediaSort
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.model.VoiceActorDetailInfo
import com.chs.youranimelist.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow

class ActorRepositoryImpl(private val apolloClient: ApolloClient) : ActorRepository {

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