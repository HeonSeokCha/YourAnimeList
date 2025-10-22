package com.chs.youranimelist.presentation.browse.actor

import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.domain.model.VoiceActorDetailInfo
import com.chs.youranimelist.presentation.UiConst

data class ActorDetailState(
    val actorDetailInfo: VoiceActorDetailInfo? = null,
    val tabNames: List<String> = UiConst.ACTOR_DETAIL_TAB_LIST,
    val tabIdx: Int = 0,
    val selectOption: SortType = SortType.NEWEST,
    val isLoading: Boolean = false,
    val isAppendLoading: Boolean = false
)