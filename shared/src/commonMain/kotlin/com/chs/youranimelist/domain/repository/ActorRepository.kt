package com.chs.youranimelist.domain.repository

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.model.VoiceActorDetailInfo
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    suspend fun getActorDetailInfo(actorId: Int): DataResult<VoiceActorDetailInfo, DataError.RemoteError>

    fun getActorRelationAnimeList(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>>
}