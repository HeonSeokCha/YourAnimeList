package com.chs.youranimelist.presentation.browse.character

import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.domain.model.CharacterDetailInfo

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val isSaveChara: CharacterDto? = null,
    val characterDetailInfo: CharacterDetailInfo? = null
)
