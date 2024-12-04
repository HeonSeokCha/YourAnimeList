package com.chs.presentation.browse.character

import com.chs.domain.model.CharacterInfo

sealed class CharaDetailEvent {
    data object GetCharaDetailInfo : CharaDetailEvent()

    data class OnAnimeClick(
        val id: Int,
        val idMal: Int
    ) : CharaDetailEvent()

    data class OnVoiceActorClick(val id: Int) : CharaDetailEvent()
    data object OnCloseClick : CharaDetailEvent()
    data class InsertCharaInfo(val info: CharacterInfo) : CharaDetailEvent()
    data class DeleteCharaInfo(val info: CharacterInfo) : CharaDetailEvent()
}