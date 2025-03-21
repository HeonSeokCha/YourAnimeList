package com.chs.presentation.browse.character

import com.chs.domain.model.CharacterInfo

sealed class CharaDetailEvent {
    data object Idle : CharaDetailEvent()

    sealed class ClickButton {
        data class Anime(
            val id: Int,
            val idMal: Int
        ) : CharaDetailEvent()

        data class VoiceActor(val id: Int) : CharaDetailEvent()
        data object Close : CharaDetailEvent()
    }

    data class InsertCharaInfo(val info: CharacterInfo) : CharaDetailEvent()
    data class DeleteCharaInfo(val info: CharacterInfo) : CharaDetailEvent()

    data object OnError : CharaDetailEvent()
}