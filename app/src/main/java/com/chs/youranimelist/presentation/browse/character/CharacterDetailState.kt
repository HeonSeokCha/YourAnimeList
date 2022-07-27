package com.chs.youranimelist.presentation.browse.character

import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.data.model.CharacterDto

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val isSaveChara: CharacterDto? = null,
    val characterDetailInfo: CharacterQuery.Data? = null
)
