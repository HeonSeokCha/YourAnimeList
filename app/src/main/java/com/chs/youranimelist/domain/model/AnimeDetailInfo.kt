package com.chs.youranimelist.domain.model

import com.chs.fragment.AnimeBasicInfo

data class AnimeDetailInfo(
    val animeBasicInfo: AnimeBasicInfo,
    val description: String,
    val startDate: String,
    val endDate: String,
    val trailerInfo: TrailerInfo,
    val type: String,
    val genres: String,
    val synonyms: String,
    val episode: Int,
    val duration: Int,
    val chapters: Int,
    val volumes: Int,
    val hashtag: String,
    val meanScore: Int,
    val source: String,
    val animeRelationInfo: AnimeRelationInfo,
    val studioInfo: StudioInfo,
    val externalLinks: List<ExternalLinkInfo>,
    val characterList: List<CharacterInfo>
)
