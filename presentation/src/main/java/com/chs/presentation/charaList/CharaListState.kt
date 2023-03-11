package com.chs.presentation.charaList

data class CharaListState(
    val isLoading: Boolean = false,
    val charaList: List<CharacterDto> = emptyList()
)
