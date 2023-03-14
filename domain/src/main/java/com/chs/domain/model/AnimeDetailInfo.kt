package com.chs.domain.model


data class AnimeDetailInfo(
    val animeInfo: com.chs.domain.model.AnimeInfo,
    val description: String,
    val startDate: String,
    val endDate: String,
    val trailerInfo: com.chs.domain.model.TrailerInfo,
    val type: String,
    val genres: List<String?>,
    val episode: Int,
    val duration: String,
    val chapters: Int,
    val hashtag: String,
    val meanScore: Int,
    val source: String,
    val animeRelationInfo: List<com.chs.domain.model.AnimeRelationInfo>,
    val studioInfo: List<com.chs.domain.model.StudioInfo>,
    val externalLinks: List<com.chs.domain.model.ExternalLinkInfo>,
    val characterList: List<com.chs.domain.model.CharacterInfo>
)
