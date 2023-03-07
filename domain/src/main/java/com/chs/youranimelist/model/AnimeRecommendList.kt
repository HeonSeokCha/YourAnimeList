package com.chs.youranimelist.model

data class AnimeRecommendList(
    val bannerList: List<com.chs.youranimelist.model.AnimeRecommendBannerInfo>,
    val animeBasicList: List<List<com.chs.youranimelist.model.AnimeInfo>>
)
