package com.chs.youranimelist.model


data class AnimeDetailInfo(
    val animeInfo: com.chs.youranimelist.model.AnimeInfo,
    val description: String,
    val startDate: String,
    val endDate: String,
    val trailerInfo: com.chs.youranimelist.model.TrailerInfo,
    val type: String,
    val genres: List<String?>,
    val episode: Int,
    val duration: String,
    val chapters: Int,
    val hashtag: String,
    val meanScore: Int,
    val source: String,
    val animeRelationInfo: List<com.chs.youranimelist.model.AnimeRelationInfo>,
    val studioInfo: List<com.chs.youranimelist.model.StudioInfo>,
    val externalLinks: List<com.chs.youranimelist.model.ExternalLinkInfo>,
    val characterList: List<com.chs.youranimelist.model.CharacterInfo>
)
