package com.chs.domain.model


data class AnimeDetailInfo(
    val animeInfo: AnimeInfo,
    val titleEnglish: String,
    val titleNative: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val trailerInfo: TrailerInfo?,
    val bannerImage: String?,
    val type: String,
    val genres: List<String?>,
    val episode: Int,
    val duration: Int,
    val chapters: Int,
    val popularScore: Int,
    val meanScore: Int,
    val source: String,
    val animeRelationInfo: List<AnimeRelationInfo>,
    val studioInfo: StudioInfo,
    val externalLinks: List<ExternalLinkInfo>,
    val characterList: List<CharacterInfo>
)
