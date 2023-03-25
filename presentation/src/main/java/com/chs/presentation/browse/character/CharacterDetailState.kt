package com.chs.presentation.browse.character

import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val isSaveChara: CharacterInfo? = null,
    val characterDetailInfo: CharacterDetailInfo? = null
)
