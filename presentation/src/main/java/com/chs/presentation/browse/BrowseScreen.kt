package com.chs.presentation.browse

sealed class BrowseScreen(
    val route: String
) {
    data object AnimeDetailScreen : BrowseScreen("anime_detail_screen")
    data object CharacterDetailScreen : BrowseScreen("character_detail_screen")
    data object StudioDetailScreen : BrowseScreen("studio_detail_screen")
}