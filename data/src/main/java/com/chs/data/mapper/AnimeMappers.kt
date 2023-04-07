package com.chs.data.mapper

import com.chs.*
import com.chs.data.model.JikanAnimeDataDto
import com.chs.data.source.db.model.AnimeEntity
import com.chs.fragment.AnimeBasicInfo
import com.chs.domain.model.*

fun convertAnimeBasicInfo(animeBasicInfo: AnimeBasicInfo?): AnimeInfo {
    return AnimeInfo(
        id = animeBasicInfo?.id ?: 0,
        idMal = animeBasicInfo?.idMal ?: 0,
        title = animeBasicInfo?.title?.romaji ?: animeBasicInfo?.title?.english ?: "",
        imageUrl = animeBasicInfo?.coverImage?.extraLarge,
        imagePlaceColor = animeBasicInfo?.coverImage?.color,
        averageScore = animeBasicInfo?.averageScore ?: 0,
        favourites = animeBasicInfo?.favourites ?: 0,
        seasonYear = animeBasicInfo?.seasonYear ?: 0,
        format = animeBasicInfo?.format?.name ?: "",
        status = animeBasicInfo?.status?.rawValue ?: "Unknown"
    )
}

fun HomeAnimeListQuery.Data?.toAnimeRecommendList(): AnimeRecommendList {
    return AnimeRecommendList(
        bannerList = this?.viewPager?.media?.map {
            with(it?.animeBasicInfo) {
                AnimeRecommendBannerInfo(
                    animeInfo = convertAnimeBasicInfo(this),
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
                    convertAnimeBasicInfo(this)
                }
            } ?: emptyList(),
            this?.popular?.media?.map {
                with(it?.animeBasicInfo) {
                    convertAnimeBasicInfo(this)
                }
            } ?: emptyList(),
            this?.upComming?.media?.map {
                with(it?.animeBasicInfo) {
                    convertAnimeBasicInfo(this)
                }
            } ?: emptyList(),
            this?.allTime?.media?.map {
                with(it?.animeBasicInfo) {
                    convertAnimeBasicInfo(this)
                }
            } ?: emptyList(),
            this?.topList?.media?.map {
                with(it?.animeBasicInfo) {
                    convertAnimeBasicInfo(this)
                }
            } ?: emptyList(),
        )
    )
}

fun AnimeDetailInfoQuery.Data.toAnimeDetailInfo(): AnimeDetailInfo {
    return with(this.Media) {
        AnimeDetailInfo(
            animeInfo = convertAnimeBasicInfo(this?.animeBasicInfo),
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
            popularScore = this?.popularity ?: 0,
            meanScore = this?.meanScore ?: 0,
            source = this?.source?.rawValue ?: "Unknown",
            animeRelationInfo = this?.relations?.edges?.map {
                AnimeRelationInfo(
                    relationType = it?.relationType?.rawValue ?: "Unknown",
                    animeBasicInfo = convertAnimeBasicInfo(it?.node?.animeBasicInfo)
                )
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
                    id = it?.characterBasicInfo?.id ?: 0,
                    name = it?.characterBasicInfo?.name?.full ?: "",
                    nativeName = it?.characterBasicInfo?.name?.native ?: "",
                    imageUrl = it?.characterBasicInfo?.image?.large,
                    favorites = it?.characterBasicInfo?.favourites ?: 0
                )
            } ?: emptyList()
        )
    }
}

fun AnimeRecommendQuery.Node.toAnimeInfo(): AnimeInfo {
    return convertAnimeBasicInfo(this.mediaRecommendation?.animeBasicInfo)
}

fun JikanAnimeDataDto.toAnimeThemeInfo(): AnimeThemeInfo {
    return AnimeThemeInfo(
        openingThemes = this.data.openingThemes,
        endingThemes = this.data.endingThemes
    )
}

fun SearchAnimeQuery.Medium.toAnimeInfo(): AnimeInfo {
    return convertAnimeBasicInfo(this.animeBasicInfo)
}

fun AnimeListQuery.Medium.toAnimeInfo(): AnimeInfo {
    return convertAnimeBasicInfo(this.animeBasicInfo)
}

fun AnimeInfo.toAnimeEntity(): AnimeEntity {
    return AnimeEntity(
        id = this.id,
        idMal = this.idMal,
        title = this.title,
        imageUrl = this.imageUrl,
        imagePlaceColor = this.imagePlaceColor,
        averageScore = this.averageScore,
        seasonYear = this.seasonYear,
        favourites = this.favourites,
        status = this.status,
        format = this.format
    )
}

fun AnimeEntity.toAnimeInfo(): AnimeInfo {
    return AnimeInfo(
        id = this.id,
        idMal = this.idMal,
        title = this.title,
        imageUrl = this.imageUrl,
        imagePlaceColor = this.imagePlaceColor,
        averageScore = this.averageScore,
        seasonYear = this.seasonYear,
        favourites = this.favourites,
        status = this.status,
        format = this.format
    )
}