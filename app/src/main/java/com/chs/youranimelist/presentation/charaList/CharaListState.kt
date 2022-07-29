package com.chs.youranimelist.presentation.charaList

import com.chs.youranimelist.data.model.CharacterDto

data class CharaListState(
    val isLoading: Boolean = false,
    val charaList: List<CharacterDto> = emptyList()
)
