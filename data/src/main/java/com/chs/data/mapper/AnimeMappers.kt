package com.chs.data.mapper

import com.chs.AnimeDetailInfoQuery
import com.chs.HomeAnimeListQuery
import com.chs.data.model.JikanAnimeDataDto
import com.chs.data.source.db.model.AnimeEntity
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeRecommendBannerInfo
import com.chs.domain.model.AnimeRecommendList
import com.chs.domain.model.AnimeRelationInfo
import com.chs.domain.model.AnimeThemeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.model.ExternalLinkInfo
import com.chs.domain.model.GenreInfo
import com.chs.domain.model.StudioInfo
import com.chs.domain.model.TrailerInfo
import com.chs.fragment.AnimeBasicInfo

fun AnimeBasicInfo?.toAnimeInfo(): AnimeInfo {
    return AnimeInfo(
        id = this?.id ?: 0,
        idMal = this?.idMal ?: 0,
        title = this?.title?.romaji ?: this?.title?.english ?: "",
        imageUrl = this?.coverImage?.extraLarge,
        imagePlaceColor = this?.coverImage?.color,
        averageScore = this?.averageScore ?: 0,
        favourites = this?.favourites ?: 0,
        season = this?.season?.rawValue ?: "Unknown",
        seasonYear = this?.seasonYear ?: 0,
        format = this?.format?.name ?: "",
        status = this?.status?.rawValue ?: "Unknown"
    )
}

fun HomeAnimeListQuery.Data?.toAnimeRecommendList(): AnimeRecommendList {
    return AnimeRecommendList(
        bannerList = this?.viewPager?.media?.map {
            with(it?.animeBasicInfo) {
                AnimeRecommendBannerInfo(
                    animeInfo = this.toAnimeInfo(),
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
                    this.toAnimeInfo()
                }
            } ?: emptyList(),
            this?.popular?.media?.map {
                with(it?.animeBasicInfo) {
                    this.toAnimeInfo()
                }
            } ?: emptyList(),
            this?.upComming?.media?.map {
                with(it?.animeBasicInfo) {
                    this.toAnimeInfo()
                }
            } ?: emptyList(),
            this?.allTime?.media?.map {
                with(it?.animeBasicInfo) {
                    this.toAnimeInfo()
                }
            } ?: emptyList(),
            this?.topList?.media?.map {
                with(it?.animeBasicInfo) {
                    this.toAnimeInfo()
                }
            } ?: emptyList(),
        )
    )
}

fun AnimeDetailInfoQuery.Data.toAnimeDetailInfo(): AnimeDetailInfo {
    return with(this.Media) {
        AnimeDetailInfo(
            animeInfo = this?.animeBasicInfo.toAnimeInfo(),
            titleEnglish = this?.title?.english ?: "",
            titleNative = this?.title?.native ?: "",
            description = this?.description ?: "",
            bannerImage = this?.bannerImage,
            startDate = if (this?.startDate?.year == null || this.startDate.month == null || this.startDate.day == null) ""
            else "${this.startDate.year}/${this.startDate.month}/${this.startDate.day}",
            endDate = if (this?.endDate?.year == null || this.endDate.month == null || this.endDate.day == null) ""
            else "${this.endDate.year}/${this.endDate.month}/${this.endDate.day}",
            trailerInfo = TrailerInfo(
                id = this?.trailer?.id ?: "",
                thumbnailUrl = this?.trailer?.thumbnail
            ),
            type = this?.type?.rawValue ?: "Unknown",
            genres = this?.genres ?: emptyList(),
            episode = this?.episodes ?: 0,
            duration = this?.duration ?: 0,
            chapters = this?.chapters ?: 0,
            popularScore = this?.popularity ?: 0,
            meanScore = this?.meanScore ?: 0,
            source = this?.source?.rawValue ?: "Unknown",
            animeRelationInfo = this?.relations?.edges?.map {
                AnimeRelationInfo(
                    relationType = it?.relationType?.rawValue ?: "Unknown",
                    animeBasicInfo = it?.node?.animeBasicInfo.toAnimeInfo()
                )
            } ?: emptyList(),
            studioInfo = if (this?.studios?.nodes.isNullOrEmpty()) {
                null
            } else {
                StudioInfo(
                    id = this?.studios?.nodes?.get(0)?.id ?: 0,
                    name = this?.studios?.nodes?.get(0)?.name ?: ""
                )
            },
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
                    favourites = it?.characterBasicInfo?.favourites ?: 0
                )
            } ?: emptyList()
        )
    }
}

fun JikanAnimeDataDto.toAnimeThemeInfo(): AnimeThemeInfo {
    return AnimeThemeInfo(
        openingThemes = this.data.openingThemes,
        endingThemes = this.data.endingThemes
    )
}

fun AnimeInfo.toAnimeEntity(): AnimeEntity {
    return AnimeEntity(
        id = this.id,
        idMal = this.idMal,
        title = this.title,
        imageUrl = this.imageUrl,
        imagePlaceColor = this.imagePlaceColor,
        averageScore = this.averageScore,
        season = this.season,
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
        season = this.season,
        seasonYear = this.seasonYear,
        favourites = this.favourites,
        status = this.status,
        format = this.format
    )
}