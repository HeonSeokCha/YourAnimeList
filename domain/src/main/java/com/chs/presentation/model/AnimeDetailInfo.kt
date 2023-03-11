package com.chs.presentation.model


data class AnimeDetailInfo(
    val animeInfo: com.chs.presentation.model.AnimeInfo,
    val description: String,
    val startDate: String,
    val endDate: String,
    val trailerInfo: com.chs.presentation.model.TrailerInfo,
    val type: String,
    val genres: List<String?>,
    val episode: Int,
    val duration: String,
    val chapters: Int,
    val hashtag: String,
    val meanScore: Int,
    val source: String,
    val animeRelationInfo: List<com.chs.presentation.model.AnimeRelationInfo>,
    val studioInfo: List<com.chs.presentation.model.StudioInfo>,
    val externalLinks: List<com.chs.presentation.model.ExternalLinkInfo>,
    val characterList: List<com.chs.presentation.model.CharacterInfo>
)
