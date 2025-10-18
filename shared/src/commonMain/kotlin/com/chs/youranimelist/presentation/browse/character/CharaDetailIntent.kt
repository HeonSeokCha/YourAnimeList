package com.chs.youranimelist.presentation.browse.character

import com.chs.youranimelist.domain.model.CharacterInfo

sealed interface CharaDetailIntent {
    data class ClickAnime(
        val id: Int,
        val idMal: Int
    ) : CharaDetailIntent

    data class ClickChara(val id: Int) : CharaDetailIntent
    data class ClickSaved(val info: CharacterInfo) : CharaDetailIntent

    data class ClickLink(val url: String) : CharaDetailIntent
    data class ClickVoiceActor(val id: Int) : CharaDetailIntent
    data class ClickSpoiler(val desc: String) : CharaDetailIntent
    data object ClickExpand : CharaDetailIntent
    data object ClickClose : CharaDetailIntent
    data object ClickDialogConfirm : CharaDetailIntent

    data object OnLoad : CharaDetailIntent
    data object OnLoadComplete : CharaDetailIntent
    data object OnAppendLoad : CharaDetailIntent
    data object OnAppendLoadComplete : CharaDetailIntent

    data object OnError : CharaDetailIntent
}