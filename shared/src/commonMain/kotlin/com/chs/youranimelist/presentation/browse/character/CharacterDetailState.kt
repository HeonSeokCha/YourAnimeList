package com.chs.youranimelist.presentation.browse.character

import app.cash.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterDetailInfo
import kotlinx.coroutines.flow.Flow

data class CharacterDetailState(
    val characterDetailInfo: CharacterDetailInfo? = null,
    val animeList: Flow<PagingData<AnimeInfo>>? = null,
    val isSave: Boolean? = null,
)