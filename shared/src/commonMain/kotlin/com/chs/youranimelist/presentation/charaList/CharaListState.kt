package com.chs.youranimelist.presentation.charaList

import com.chs.domain.model.CharacterInfo

data class CharaListState(
    val isLoading: Boolean = true,
    val list: List<CharacterInfo> = emptyList()
)