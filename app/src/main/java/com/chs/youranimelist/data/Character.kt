package com.chs.youranimelist.data

data class Character(
    val id: Int,
    val name: String = "",
    val nativeName: String = "",
    val image: String? = null,
    val favourites: Int? = null,
    val created: String = ""
)
