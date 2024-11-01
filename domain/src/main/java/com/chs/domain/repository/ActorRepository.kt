package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.common.Resource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.model.VoiceActorDetailInfo
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    fun getActorDetailInfo(actorId: Int): Flow<Resource<VoiceActorDetailInfo>>

    fun getActorRelationAnimeList(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<AnimeInfo>>

    fun getActorRelationCharaList(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<CharacterInfo>>
}