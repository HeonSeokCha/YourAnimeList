package com.chs.youranimelist.presentation.browse.character

import com.chs.youranimelist.model.CharacterDetailInfo

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val isSaveChara: CharacterDto? = null,
    val characterDetailInfo: com.chs.youranimelist.model.CharacterDetailInfo? = null
)
