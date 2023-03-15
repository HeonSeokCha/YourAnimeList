package com.chs.domain.model

data class AnimeRecommendList(
    val bannerList: List<AnimeRecommendBannerInfo>,
    val animeBasicList: List<List<AnimeInfo>>
)
