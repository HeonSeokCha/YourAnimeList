package com.chs.youranimelist.presentation.browse

import com.chs.youranimelist.domain.model.SortFilter
import kotlinx.serialization.Serializable

@Serializable
sealed interface BrowseScreen {

    @Serializable
    data class AnimeDetail(
        val id: Int,
        val idMal: Int
    ) : BrowseScreen

    @Serializable
    data class CharacterDetail(val id: Int) : BrowseScreen

    @Serializable
    data class StudioDetail(val id: Int) : BrowseScreen

    @Serializable
    data class ActorDetail(val id: Int) : BrowseScreen

    @Serializable
    data class SortList(val filter: SortFilter) : BrowseScreen
}