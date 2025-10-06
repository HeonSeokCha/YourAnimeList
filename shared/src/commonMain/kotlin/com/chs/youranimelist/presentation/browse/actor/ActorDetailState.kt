package com.chs.youranimelist.presentation.browse.actor

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.model.VoiceActorDetailInfo
import com.chs.youranimelist.presentation.UiConst
import kotlinx.coroutines.flow.Flow

data class ActorDetailState(
    val actorDetailInfo: VoiceActorDetailInfo? = null,
    val actorAnimeList: Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>>? = null,
    val tabNames: List<String> = UiConst.ACTOR_DETAIL_TAB_LIST,
    val tabIdx: Int = 0,
    val selectOption: UiConst.SortType = UiConst.SortType.NEWEST,
    val isLoading: Boolean = true,
)