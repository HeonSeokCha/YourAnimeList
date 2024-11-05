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
    val tabNames: List<String> = UiConst.ANIME_DETAIL_TAB_LIST,
    val animeRecList: Flow<PagingData<AnimeInfo>>? = null,
    val isSave: Boolean = false
)

