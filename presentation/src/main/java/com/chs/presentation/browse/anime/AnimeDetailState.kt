package com.chs.presentation.browse.anime

import androidx.paging.PagingData
import com.chs.common.Resource
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeThemeInfo
import com.chs.presentation.UiConst
import kotlinx.coroutines.flow.Flow

data class AnimeDetailState(
    val animeDetailInfo: Resource<AnimeDetailInfo> = Resource.Loading(),
    val animeThemes: Resource<AnimeThemeInfo> = Resource.Loading(),
    val animeRecList: Flow<PagingData<AnimeInfo>>? = null,
    val selectTabIdx: Int = 0,
    val isSave: Boolean = false
)

