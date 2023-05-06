package com.chs.presentation.browse.character

import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val isSave: Boolean = false,
    val characterDetailInfo: CharacterDetailInfo? = null
)
