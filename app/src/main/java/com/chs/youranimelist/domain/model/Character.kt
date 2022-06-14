package com.chs.youranimelist.domain.model

data class Character(
    val charaId: Int,
    val name: String? = null,
    val nativeName: String? = null,
    val image: String? = null,
    val favourites: Int? = null,
)
