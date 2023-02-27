package com.chs.youranimelist.data.mapper

import com.chs.AnimeDetailInfoQuery
import com.chs.HomeAnimeListQuery
import com.chs.youranimelist.domain.model.*

fun HomeAnimeListQuery.Data?.toAnimeRecommendList(): AnimeRecommendList {
    return AnimeRecommendList(
        bannerList = this?.viewPager?.media?.map {
            with(it?.animeBasicInfo) {
                AnimeRecommendBannerInfo(
                    animeInfo = AnimeInfo(
                        id = this?.id ?: 0,
                        idMal = this?.idMal ?: 0,
                        title = TitleInfo(
                            romaji = this?.title?.romaji,
                            native = this?.title?.native,
                            english = this?.title?.english ?: "No title"
                        ),
                        imageInfo = ImageInfo(
                            url = this?.coverImage?.extraLarge,
                            color = this?.coverImage?.color
                        ),
                        averageScore = this?.averageScore ?: 0,
                        favoriteScore = this?.favourites ?: 0,
                        season = this?.season?.rawValue ?: "UnKnown",
                        seasonYear = this?.seasonYear ?: 0,
                        format = this?.format?.rawValue ?: "UnKnown",
                        status = this?.status?.rawValue ?: "Unknown"
                    ),
                    trailer = TrailerInfo(
                        id = it?.trailer?.id ?: "",
                        thumbnailUrl = it?.trailer?.thumbnail
                    )
                )
            }
        } ?: emptyList(),
        animeBasicList = listOf(
            this?.trending?.media?.map {
                with(it?.animeBasicInfo) {
                    AnimeInfo(
                        id = this?.id ?: 0,
                        idMal = this?.idMal ?: 0,
                        title = TitleInfo(
                            romaji = this?.title?.romaji,
                            native = this?.title?.native,
                            english = this?.title?.english ?: "No title"
                        ),
                        imageInfo = ImageInfo(
                            url = this?.coverImage?.extraLarge,
                            color = this?.coverImage?.color
                        ),
                        averageScore = this?.averageScore ?: 0,
                        favoriteScore = this?.favourites ?: 0,
                        season = this?.season?.rawValue ?: "UnKnown",
                        seasonYear = this?.seasonYear ?: 0,
                        format = this?.format?.rawValue ?: "UnKnown",
                        status = this?.status?.rawValue ?: "Unknown"
                    )
                }
            } ?: emptyList(),
            this?.popular?.media?.map {
                with(it?.animeBasicInfo) {
                    AnimeInfo(
                        id = this?.id ?: 0,
                        idMal = this?.idMal ?: 0,
                        title = TitleInfo(
                            romaji = this?.title?.romaji,
                            native = this?.title?.native,
                            english = this?.title?.english ?: "No title"
                        ),
                        imageInfo = ImageInfo(
                            url = this?.coverImage?.extraLarge,
                            color = this?.coverImage?.color
                        ),
                        averageScore = this?.averageScore ?: 0,
                        favoriteScore = this?.favourites ?: 0,
                        season = this?.season?.rawValue ?: "UnKnown",
                        seasonYear = this?.seasonYear ?: 0,
                        format = this?.format?.rawValue ?: "UnKnown",
                        status = this?.status?.rawValue ?: "Unknown"
                    )
                }
            } ?: emptyList(),
            this?.upComming?.media?.map {
                with(it?.animeBasicInfo) {
                    AnimeInfo(
                        id = this?.id ?: 0,
                        idMal = this?.idMal ?: 0,
                        title = TitleInfo(
                            romaji = this?.title?.romaji,
                            native = this?.title?.native,
                            english = this?.title?.english ?: "No title"
                        ),
                        imageInfo = ImageInfo(
                            url = this?.coverImage?.extraLarge,
                            color = this?.coverImage?.color
                        ),
                        averageScore = this?.averageScore ?: 0,
                        favoriteScore = this?.favourites ?: 0,
                        season = this?.season?.rawValue ?: "UnKnown",
                        seasonYear = this?.seasonYear ?: 0,
                        format = this?.format?.rawValue ?: "UnKnown",
                        status = this?.status?.rawValue ?: "Unknown"
                    )
                }
            } ?: emptyList(),
            this?.trending?.media?.map {
                with(it?.animeBasicInfo) {
                    AnimeInfo(
                        id = this?.id ?: 0,
                        idMal = this?.idMal ?: 0,
                        title = TitleInfo(
                            romaji = this?.title?.romaji,
                            native = this?.title?.native,
                            english = this?.title?.english ?: "No title"
                        ),
                        imageInfo = ImageInfo(
                            url = this?.coverImage?.extraLarge,
                            color = this?.coverImage?.color
                        ),
                        averageScore = this?.averageScore ?: 0,
                        favoriteScore = this?.favourites ?: 0,
                        season = this?.season?.rawValue ?: "UnKnown",
                        seasonYear = this?.seasonYear ?: 0,
                        format = this?.format?.rawValue ?: "UnKnown",
                        status = this?.status?.rawValue ?: "Unknown"
                    )
                }
            } ?: emptyList(),
            this?.trending?.media?.map {
                with(it?.animeBasicInfo) {
                    AnimeInfo(
                        id = this?.id ?: 0,
                        idMal = this?.idMal ?: 0,
                        title = TitleInfo(
                            romaji = this?.title?.romaji,
                            native = this?.title?.native,
                            english = this?.title?.english ?: "No title"
                        ),
                        imageInfo = ImageInfo(
                            url = this?.coverImage?.extraLarge,
                            color = this?.coverImage?.color
                        ),
                        averageScore = this?.averageScore ?: 0,
                        favoriteScore = this?.favourites ?: 0,
                        season = this?.season?.rawValue ?: "UnKnown",
                        seasonYear = this?.seasonYear ?: 0,
                        format = this?.format?.rawValue ?: "UnKnown",
                        status = this?.status?.rawValue ?: "Unknown"
                    )
                }
            } ?: emptyList(),
        )
    )
}

fun AnimeDetailInfoQuery.Data.toAnimeDetailInfo(): AnimeDetailInfo {
    return with(this.Media) {
        AnimeDetailInfo(
            animeInfo = with(this?.animeBasicInfo) {
                AnimeInfo(
                    id = this?.id ?: 0,
                    idMal = this?.idMal ?: 0,
                    title = TitleInfo(
                        romaji = this?.title?.romaji,
                        native = this?.title?.native,
                        english = this?.title?.english ?: "No title"
                    ),
                    imageInfo = ImageInfo(
                        url = this?.coverImage?.extraLarge,
                        color = this?.coverImage?.color
                    ),
                    averageScore = this?.averageScore ?: 0,
                    favoriteScore = this?.favourites ?: 0,
                    season = this?.season?.rawValue ?: "UnKnown",
                    seasonYear = this?.seasonYear ?: 0,
                    format = this?.format?.rawValue ?: "UnKnown",
                    status = this?.status?.rawValue ?: "Unknown"
                )
            },
            description = this?.description ?: "",
            startDate = "${this?.startDate?.year ?: 0}/${this?.startDate?.month ?: 0}/${this?.startDate?.day ?: 0}",
            endDate = "${this?.endDate?.year ?: 0}/${this?.endDate?.month ?: 0}/${this?.endDate?.day ?: 0}",
            trailerInfo = TrailerInfo(
                id = this?.trailer?.id ?: "",
                thumbnailUrl = this?.trailer?.thumbnail
            ),
            type = this?.type?.rawValue ?: "Unknown",
            genres = this?.genres ?: emptyList(),
            episode = this?.episodes ?: 0,
            duration = "${this?.duration ?: 0}Min",
            chapters = this?.chapters ?: 0,
            hashtag = this?.hashtag ?: "",
            meanScore = this?.meanScore ?: 0,
            source = this?.source?.rawValue ?: "Unknown",
            animeRelationInfo = this?.relations?.edges?.map {
                with(it?.node?.animeBasicInfo) {
                    AnimeRelationInfo(
                        relationType = it?.relationType?.rawValue ?: "Unknown",
                        animeBasicInfo = AnimeInfo(
                            id = this?.id ?: 0,
                            idMal = this?.idMal ?: 0,
                            title = TitleInfo(
                                romaji = this?.title?.romaji,
                                native = this?.title?.native,
                                english = this?.title?.english ?: "No title"
                            ),
                            imageInfo = ImageInfo(
                                url = this?.coverImage?.extraLarge,
                                color = this?.coverImage?.color
                            ),
                            averageScore = this?.averageScore ?: 0,
                            favoriteScore = this?.favourites ?: 0,
                            season = this?.season?.rawValue ?: "UnKnown",
                            seasonYear = this?.seasonYear ?: 0,
                            format = this?.format?.rawValue ?: "UnKnown",
                            status = this?.status?.rawValue ?: "Unknown"
                        )
                    )
                }
            } ?: emptyList(),
            studioInfo = this?.studios?.studiosEdges?.map {
                with(it?.studiosNode) {
                    StudioInfo(
                        id = this?.id ?: 0,
                        isMainStudio = it?.isMain ?: false,
                        name = this?.name ?: ""
                    )
                }
            } ?: emptyList(),
            externalLinks = this?.externalLinks?.map {
                ExternalLinkInfo(
                    color = it?.color ?: "#ffffff",
                    iconUrl = it?.icon,
                    siteName = it?.site ?: "Unknown",
                    linkUrl = it?.url ?: ""
                )
            } ?: emptyList(),
            characterList = this?.characters?.nodes?.map {
                CharacterInfo(
                    id = it?.id ?: 0,
                    name = it?.name?.full ?: "",
                    image = it?.image?.large
                )
            } ?: emptyList()
        )
    }
}