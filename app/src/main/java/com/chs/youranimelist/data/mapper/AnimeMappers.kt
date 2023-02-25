package com.chs.youranimelist.data.mapper

import com.chs.HomeAnimeListQuery
import com.chs.youranimelist.domain.model.*

fun HomeAnimeListQuery.Data?.toAnimeRecommendList(): AnimeRecommendList {
    return AnimeRecommendList(
        bannerList = this?.viewPager?.media?.map {
            AnimeRecommendBannerInfo(
                animeInfo = AnimeInfo(
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
                    seasonYear = it?.animeBasicInfo?.seasonYear ?: 0,
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
            this?.trending?.media?.map {
                AnimeInfo(
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
                    seasonYear = it?.animeBasicInfo?.seasonYear ?: 0,
                    format = it?.animeBasicInfo?.format?.rawValue ?: "UnKnown",
                    status = it?.animeBasicInfo?.status?.rawValue ?: "Unknown"
                )
            } ?: emptyList(),
            this?.popular?.media?.map {
                AnimeInfo(
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
                    seasonYear = it?.animeBasicInfo?.seasonYear ?: 0,
                    format = it?.animeBasicInfo?.format?.rawValue ?: "UnKnown",
                    status = it?.animeBasicInfo?.status?.rawValue ?: "Unknown"
                )
            } ?: emptyList(),
            this?.upComming?.media?.map {
                AnimeInfo(
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
                    seasonYear = it?.animeBasicInfo?.seasonYear ?: 0,
                    format = it?.animeBasicInfo?.format?.rawValue ?: "UnKnown",
                    status = it?.animeBasicInfo?.status?.rawValue ?: "Unknown"
                )
            } ?: emptyList(),
            this?.trending?.media?.map {
                AnimeInfo(
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
                    seasonYear = it?.animeBasicInfo?.seasonYear ?: 0,
                    format = it?.animeBasicInfo?.format?.rawValue ?: "UnKnown",
                    status = it?.animeBasicInfo?.status?.rawValue ?: "Unknown"
                )
            } ?: emptyList(),
            this?.trending?.media?.map {
                AnimeInfo(
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
                    seasonYear = it?.animeBasicInfo?.seasonYear ?: 0,
                    format = it?.animeBasicInfo?.format?.rawValue ?: "UnKnown",
                    status = it?.animeBasicInfo?.status?.rawValue ?: "Unknown"
                )
            } ?: emptyList(),
        )
    )
}