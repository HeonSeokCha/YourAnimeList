package com.chs.youranimelist.domain.model

data class CharacterDetailInfo(
    val characterInfo: CharacterInfo,
    val spoilerDesc: String? = null,
    val birthDay: String,
    val age: String,
    val gender: String,
    val bloodType: String,
    val voiceActorInfo: List<VoiceActorInfo?>? = null,
)
