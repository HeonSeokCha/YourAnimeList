package com.chs.youranimelist.domain.model

data class AnimeSavedInfo(
    override val id: Int,
    override val idMal: Int,
    override val title: String,
    override val imageUrl: String?,
    override val imagePlaceColor: String?,
    override val averageScore: Int,
    override val favourites: Int
) : BaseAnimeInfo
