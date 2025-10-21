package com.chs.youranimelist.presentation.browse.studio

import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.domain.model.StudioDetailInfo

data class StudioDetailState(
    val studioDetailInfo: StudioDetailInfo? = null,
    val sortOption: SortType = SortType.NEWEST,
    val isPagingLoading: Boolean = false,
    val isPagingAppendLoading: Boolean = false
)
