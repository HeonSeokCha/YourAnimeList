package com.chs.presentation.charaList

import com.chs.domain.model.CharacterInfo

data class CharaListState(
    val charaList: List<CharacterInfo> = emptyList()
)
