package com.chs.youranimelist.domain.model

data class VoiceActorInfo(
    val id: Int,
    val name: String,
    val nativeName: String,
    val imageUrl: String?,
    val language: String
)
