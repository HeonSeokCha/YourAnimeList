package com.chs.youranimelist.presentation.browse.character

import com.chs.youranimelist.CharacterQuery

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val characterDetailInfo: CharacterQuery.Data? = null
)
