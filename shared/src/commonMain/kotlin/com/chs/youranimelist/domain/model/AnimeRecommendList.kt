package com.chs.youranimelist.domain.model

data class AnimeRecommendList(
    val bannerList: List<AnimHomeBannerInfo>,
    val animeBasicList: List<List<AnimeInfo>>
)
