package com.chs.youranimelist.presentation.browse

sealed class BrowseScreen(
    val route: String
) {
    object AnimeDetailScreen : BrowseScreen("anime_detail_screen")
    object CharacterDetailScreen : BrowseScreen("character_detail_screen")
    object StudioDetailScreen : BrowseScreen("studio_detail_screen")
}