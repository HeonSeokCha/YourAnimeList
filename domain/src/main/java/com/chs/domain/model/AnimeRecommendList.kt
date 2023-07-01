package com.chs.domain.model

data class AnimeRecommendList(
    val bannerList: List<AnimHomeBannerInfo>,
    val animeBasicList: List<List<AnimeInfo>>
)
