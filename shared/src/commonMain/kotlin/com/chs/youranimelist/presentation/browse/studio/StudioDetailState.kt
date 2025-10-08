package com.chs.youranimelist.presentation.browse.studio

import androidx.paging.PagingData
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.domain.model.StudioDetailInfo
import kotlinx.coroutines.flow.Flow

data class StudioDetailState(
    val studioDetailInfo: StudioDetailInfo? = null,
    val studioAnimeList: Flow<PagingData<AnimeInfo>>? = null,
    val tabIdx: Int = 0,
    val sortOption: SortType = SortType.NEWEST,
    val isLoading: Boolean = true,
)
