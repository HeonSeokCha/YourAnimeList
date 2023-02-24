package com.chs.youranimelist.data.mapper

import com.chs.HomeAnimeListQuery
import com.chs.youranimelist.domain.model.*

fun HomeAnimeListQuery.Data.toAnimeRecommendList(): AnimeRecommendList {
    return AnimeRecommendList(
        bannerList = viewPager?.media?.map {
            AnimeRecommendBannerInfo(
                animeBasicInfo = AnimeBasicInfo(
                    id = it?.animeBasicInfo?.id ?: 0,
                    idMal = it?.animeBasicInfo?.idMal ?: 0,
                    title = TitleInfo(
                        romaji = it?.animeBasicInfo?.title?.romaji,
                        native = it?.animeBasicInfo?.title?.native,
                        english = it?.animeBasicInfo?.title?.english ?: "No title"
                    ),
                    imageInfo = ImageInfo(
                        url = it?.animeBasicInfo?.coverImage?.extraLarge,
                        color = it?.animeBasicInfo?.coverImage?.color
                    ),
                    averageScore = it?.animeBasicInfo?.averageScore ?: 0,
                    favoriteScore = it?.animeBasicInfo?.favourites ?: 0,
                    season = it?.animeBasicInfo?.season?.rawValue ?: "UnKnown",
                    format = it?.animeBasicInfo?.format?.rawValue ?: "UnKnown",
                    status = it?.animeBasicInfo?.status?.rawValue ?: "Unknown"
                ),
                trailer = TrailerInfo(
                    id = it?.trailer?.id ?: "",
                    thumbnailUrl = it?.trailer?.thumbnail
                )
            )
        } ?: emptyList(),

        animeBasicList = listOf(
            trending?.media?.map {
                AnimeBasicInfo()
            } ?: emptyList(),
            popular?.media?.map {
                AnimeBasicInfo()
            } ?: emptyList(),

            upComming?.media?.map {
                AnimeBasicInfo()
            } ?: emptyList(),
            trending?.media?.map {
                AnimeBasicInfo()
            } ?: emptyList(),

            trending?.media?.map {
                AnimeBasicInfo()
            } ?: emptyList(),
        )
    )
}