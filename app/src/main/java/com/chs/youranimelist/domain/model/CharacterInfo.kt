package com.chs.youranimelist.domain.model

data class CharacterInfo(
    val id: Int,
    val name: String,
    val nativeName: String,
    val imageUrl: String?,
    val favorites: Int
)
