package com.chs.youranimelist.data.mapper

import com.chs.youranimelist.data.StudioQuery
import com.chs.youranimelist.domain.model.StudioDetailInfo
import com.chs.youranimelist.domain.model.StudioInfo

fun StudioQuery.Data.toStudioDetailInfo(): StudioDetailInfo {
    return StudioDetailInfo(
        studioBasicInfo = StudioInfo(
            id = this.studio?.id ?: 0,
            name = this.studio?.name ?: ""
        ),
        favourites = this.studio?.favourites ?: 0
    )
}