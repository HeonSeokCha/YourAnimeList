package com.chs.youranimelist.domain.repository

import app.cash.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.common.DataError
import com.chs.common.DataResult
import com.chs.youranimelist.domain.model.VoiceActorDetailInfo
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    suspend fun getActorDetailInfo(actorId: Int): DataResult<VoiceActorDetailInfo, DataError.RemoteError>

    fun getActorRelationAnimeList(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>>
}