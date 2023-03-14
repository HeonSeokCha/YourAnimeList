package com.chs.domain.model

data class AnimeRecommendList(
    val bannerList: List<com.chs.domain.model.AnimeRecommendBannerInfo>,
    val animeBasicList: List<List<com.chs.domain.model.AnimeInfo>>
)
