package com.chs.presentation.browse.character

import androidx.paging.PagingData
import com.chs.common.Resource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterDetailInfo
import kotlinx.coroutines.flow.Flow

data class CharacterDetailState(
    val characterDetailInfo: CharacterDetailInfo? = null,
    val animeList: Flow<PagingData<AnimeInfo>>? = null,
    val isRefresh: Boolean = false,
    val isSave: Boolean = false,
    val isError: String? = null
)