package com.chs.youranimelist.presentation.bottom.charaList

import com.chs.youranimelist.domain.model.CharacterInfo

data class CharaListState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val list: List<CharacterInfo> = emptyList()
)