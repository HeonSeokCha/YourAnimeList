package com.chs.presentation.charaList

import com.chs.domain.model.CharacterInfo

data class CharaListState(
    val isLoading: Boolean = false,
    val charaList: List<CharacterInfo> = emptyList()
)
