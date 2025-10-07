package com.chs.youranimelist.presentation.bottom.charaList

sealed interface CharaListEffect {
    data class NavigateCharaDetail(val id: Int) : CharaListEffect
}