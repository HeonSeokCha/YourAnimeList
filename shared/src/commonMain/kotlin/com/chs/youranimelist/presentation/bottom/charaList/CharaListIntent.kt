package com.chs.youranimelist.presentation.bottom.charaList

sealed interface CharaListIntent {
    data class ClickChara(val id: Int) : CharaListIntent
}