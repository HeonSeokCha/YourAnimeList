package com.chs.data.mapper

import com.chs.data.StudioQuery
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.model.StudioInfo

fun StudioQuery.Data.toStudioDetailInfo(): StudioDetailInfo {
    return StudioDetailInfo(
        studioBasicInfo = StudioInfo(
            id = this.studio?.id ?: 0,
            name = this.studio?.name ?: ""
        ),
        favourites = this.studio?.favourites ?: 0
    )
}