package com.chs.youranimelist.presentation.browse.studio

import androidx.paging.PagingData
import presentation.UiConst
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
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
