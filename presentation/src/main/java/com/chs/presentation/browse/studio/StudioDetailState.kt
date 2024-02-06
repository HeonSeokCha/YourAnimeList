package com.chs.presentation.browse.studio

import androidx.paging.PagingData
import com.chs.presentation.UiConst
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
import kotlinx.coroutines.flow.Flow

data class StudioDetailState(
    val studioId: Int? = null,
    val studioDetailInfo: StudioDetailInfo? = null,
    val studioAnimeList: Flow<PagingData<AnimeInfo>>? = null,
    val sortOption: UiConst.SortType = UiConst.SortType.NEWEST,
    val isLoading: Boolean = false
)
