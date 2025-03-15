package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.common.Resource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.model.DataError
import com.chs.domain.model.Result
import com.chs.domain.model.VoiceActorDetailInfo
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    suspend fun getActorDetailInfo(actorId: Int): Result<VoiceActorDetailInfo, DataError.RemoteError>

    fun getActorRelationAnimeList(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>>
}