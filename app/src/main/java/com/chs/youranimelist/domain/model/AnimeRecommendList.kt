package com.chs.youranimelist.domain.model

data class AnimeRecommendList(
    val bannerList: List<AnimeRecommendBannerInfo>,
    val animeBasicList: List<List<AnimeBasicInfo>>
)
