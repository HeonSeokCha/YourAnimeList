package com.chs.presentation.browse.anime

import androidx.paging.PagingData
import com.chs.common.Resource
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeThemeInfo
import com.chs.presentation.UiConst
import kotlinx.coroutines.flow.Flow

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailInfo? = null,
    val animeThemes: AnimeThemeInfo? = null,
    val animeRecList: Flow<PagingData<AnimeInfo>>? = null,
    val selectTabIdx: Int = 0,
    val isSave: Boolean = false,
    val isError: String? = null
)

