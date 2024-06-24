package com.chs.presentation.browse.character

import com.chs.domain.model.CharacterInfo


sealed class CharaDetailEvent {
    data object GetCharaDetailInfo : CharaDetailEvent()
    data class InsertCharaInfo(val info: CharacterInfo) : CharaDetailEvent()
    data class DeleteCharaInfo(val info: CharacterInfo) : CharaDetailEvent()
}