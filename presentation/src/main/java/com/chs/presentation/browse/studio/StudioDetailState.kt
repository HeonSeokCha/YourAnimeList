package com.chs.presentation.browse.studio

import androidx.paging.PagingData
import com.chs.common.Resource
import com.chs.presentation.UiConst
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
import kotlinx.coroutines.flow.Flow

data class StudioDetailState(
    val studioId: Int? = null,
    val studioDetailInfo: Resource<StudioDetailInfo> = Resource.Loading(),
    val studioAnimeList: Flow<PagingData<AnimeInfo>>? = null,
    val sortOption: Pair<String, String> = UiConst.SortType.NEWEST.run {
        this.name to this.rawValue
    }
)
