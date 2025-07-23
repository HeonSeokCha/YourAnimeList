package com.chs.youranimelist.presentation.browse.studio

import app.cash.paging.PagingData
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.StudioDetailInfo
import kotlinx.coroutines.flow.Flow

data class StudioDetailState(
    val studioDetailInfo: StudioDetailInfo? = null,
    val studioAnimeList: Flow<PagingData<AnimeInfo>>? = null,
    val tabIdx: Int = 0,
    val sortOption: Pair<String, String> = UiConst.SortType.NEWEST.run {
        this.name to this.rawValue
    },
    val isLoading: Boolean = true,
)
