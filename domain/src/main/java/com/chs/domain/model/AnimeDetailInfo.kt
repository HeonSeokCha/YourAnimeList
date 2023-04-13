package com.chs.domain.model


data class AnimeDetailInfo(
    val animeInfo: AnimeInfo,
    val titleEnglish: String,
    val titleNative: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val trailerInfo: TrailerInfo?,
    val type: String,
    val genres: List<String?>,
    val episode: Int,
    val duration: String,
    val chapters: Int,
    val hashtag: String,
    val popularScore: Int,
    val meanScore: Int,
    val source: String,
    val animeRelationInfo: List<AnimeRelationInfo>,
    val studioInfo: List<StudioInfo>,
    val externalLinks: List<ExternalLinkInfo>,
    val characterList: List<CharacterInfo>
)
