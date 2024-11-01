package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.chs.common.Constants
import com.chs.common.Resource
import com.chs.data.paging.VoiceActorAnimePagingSource
import com.chs.data.paging.VoiceActorCharaPagingSource
import com.chs.data.type.CharacterSort
import com.chs.data.type.MediaSort
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.model.VoiceActorDetailInfo
import com.chs.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActorRepository @Inject constructor(
    private val apolloClient: ApolloClient,
) : ActorRepository {
    override fun getActorDetailInfo(actorId: Int): Flow<Resource<VoiceActorDetailInfo>> {
        TODO("Not yet implemented")
    }

    override fun getActorRelationAnimeList(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<AnimeInfo>> {
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

    override fun getActorRelationCharaList(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<CharacterInfo>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                initialLoadSize = Constants.PAGING_SIZE * 3
            )
        ) {
            VoiceActorCharaPagingSource(
                apolloClient = apolloClient,
                voiceActorId = actorId,
                sortOptions = sortOptions.map { CharacterSort.safeValueOf(it) }
            )
        }.flow
    }
}