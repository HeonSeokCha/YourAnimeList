package com.chs.presentation.browse.character

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import kotlinx.coroutines.flow.Flow

data class CharacterDetailState(
    val characterDetailInfo: CharacterDetailInfo? = null,
    val animeList: Flow<PagingData<AnimeInfo>>? = null,
    val isLoading: Boolean = true,
    val isSave: Boolean = false,
    val isError: String? = null
)
