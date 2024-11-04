package com.chs.presentation.browse.actor

import androidx.paging.PagingData
import com.chs.common.Resource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.model.VoiceActorDetailInfo
import kotlinx.coroutines.flow.Flow

data class ActorDetailState(
    val actorDetailInfo: Resource<VoiceActorDetailInfo> = Resource.Loading(),
    val actorAnimeList: Flow<PagingData<AnimeInfo>>? = null,
    val actorCharaList: Flow<PagingData<CharacterInfo>>? = null
)