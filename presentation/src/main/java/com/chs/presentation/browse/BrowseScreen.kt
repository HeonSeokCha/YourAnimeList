package com.chs.presentation.browse

import kotlinx.serialization.Serializable

@Serializable
sealed class BrowseScreen {

    @Serializable
    data class AnimeDetailScreen(
        val id: Int,
        val idMal: Int
    ) : BrowseScreen()

    @Serializable
    data class CharacterDetailScreen(val id: Int) : BrowseScreen()

    @Serializable
    data class StudioDetailScreen(val id: Int) : BrowseScreen()
}