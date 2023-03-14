package com.chs.presentation.browse.character

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val isSaveChara: CharacterDto? = null,
    val characterDetailInfo: com.chs.domain.model.CharacterDetailInfo? = null
)
