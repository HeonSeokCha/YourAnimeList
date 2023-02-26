package com.chs.youranimelist.domain.model

data class AnimeRelationInfo(
    val relationType: String,
    val animeBasicInfoList: List<AnimeInfo>
)
