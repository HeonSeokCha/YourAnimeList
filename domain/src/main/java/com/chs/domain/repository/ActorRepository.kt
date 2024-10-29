package com.chs.domain.repository

import com.chs.common.Resource
import com.chs.domain.model.ActorDetailInfo
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    fun getActorDetailInfo(actorId: Int): Flow<Resource<ActorDetailInfo>>

    fun getActorRelationAnimeList(
        actorId: Int,

    )
}