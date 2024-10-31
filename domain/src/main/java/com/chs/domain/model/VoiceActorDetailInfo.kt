package com.chs.domain.model

data class VoiceActorDetailInfo(
    val name: String,
    val nativeName: String,
    val imageUrl: String?,
    val gender: String,
    val birthDate: String,
    val deathDate: String?,
    val homeTown: String?,
    val dateActive: String,
    val favorite: Int,
    val description: String,
    val relationCharaList: List<CharacterInfo>,
    val relationAnimeList: List<AnimeInfo>
)
