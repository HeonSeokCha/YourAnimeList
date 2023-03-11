package com.chs.presentation.model

data class AnimeRecommendList(
    val bannerList: List<com.chs.presentation.model.AnimeRecommendBannerInfo>,
    val animeBasicList: List<List<com.chs.presentation.model.AnimeInfo>>
)
