package com.chs.data.mapper

import com.chs.StudioQuery
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.model.StudioInfo

fun StudioQuery.Studio.toStudioDetailInfo(): StudioDetailInfo {
    return StudioDetailInfo(
        studioBasicInfo = StudioInfo(
            id = this.id,
            name = this.name
        ),
        favourites = this.favourites ?: 0,
    )
}