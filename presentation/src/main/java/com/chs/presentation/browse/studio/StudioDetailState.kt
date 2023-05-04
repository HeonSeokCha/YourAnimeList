package com.chs.presentation.browse.studio

import androidx.paging.PagingData
import com.chs.common.UiConst
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
import kotlinx.coroutines.flow.Flow

data class StudioDetailState(
    val studioDetailInfo: StudioDetailInfo? = null,
    val studioAnimeList: Flow<PagingData<AnimeInfo>>? = null,
    val sortOption: UiConst.SortType = UiConst.SortType.POPULARITY,
    val isLoading: Boolean = false
)
