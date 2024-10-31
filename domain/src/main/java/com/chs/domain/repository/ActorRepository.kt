package com.chs.domain.repository

import com.chs.common.Resource
import com.chs.domain.model.VoiceActorDetailInfo
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    fun getActorDetailInfo(actorId: Int): Flow<Resource<VoiceActorDetailInfo>>

    fun getActorRelationAnimeList(
        actorId: Int,

    )
}