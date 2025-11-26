package com.chs.youranimelist.presentation.browse

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class BrowseScreen : NavKey {

    @Serializable
    data class AnimeDetail(
        val id: Int,
        val idMal: Int
    ) : BrowseScreen()

    @Serializable
    data class CharacterDetail(val id: Int) : BrowseScreen()

    @Serializable
    data class StudioDetail(val id: Int) : BrowseScreen()

    @Serializable
    data class ActorDetail(val id: Int) : BrowseScreen()
}