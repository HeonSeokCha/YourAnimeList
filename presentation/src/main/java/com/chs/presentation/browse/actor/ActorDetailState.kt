package com.chs.presentation.browse.actor

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.model.VoiceActorDetailInfo
import com.chs.presentation.UiConst
import kotlinx.coroutines.flow.Flow

data class ActorDetailState(
    val actorDetailInfo: VoiceActorDetailInfo? = null,
    val actorAnimeList: Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>>? = null,
    val tabNames: List<String> = UiConst.ACTOR_DETAIL_TAB_LIST,
    val selectOption: UiConst.SortType = UiConst.SortType.NEWEST,
    val isLoading: Boolean = true,
    val isError: String? = null
)