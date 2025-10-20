package com.chs.youranimelist.presentation.browse.character

import com.chs.youranimelist.domain.model.CharacterDetailInfo

data class CharacterDetailState(
    val characterDetailInfo: CharacterDetailInfo? = null,
    val isShowDialog: Boolean = false,
    val spoilerDesc: String = "",
    val isDescExpand: Boolean = false,
    val isSave: Boolean = false,
    val isAnimeListLoading: Boolean = false,
    val isAnimeListAppendLoading: Boolean = false
)