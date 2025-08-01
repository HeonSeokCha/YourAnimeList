package com.chs.youranimelist.data.mapper

import com.chs.youranimelist.data.Util
import com.chs.youranimelist.data.model.JikanAnimeDataDto
import com.chs.youranimelist.data.source.db.entity.AnimeEntity
import com.chs.youranimelist.data.AnimeDetailInfoQuery
import com.chs.youranimelist.data.HomeAnimeListQuery
import com.chs.youranimelist.data.fragment.AnimeBasicInfo
import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.AnimHomeBannerInfo
import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.domain.model.AnimeRelationInfo
import com.chs.youranimelist.domain.model.AnimeThemeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.model.StudioInfo
import com.chs.youranimelist.domain.model.TrailerInfo
import com.chs.youranimelist.domain.model.TagInfo

fun AnimeBasicInfo?.toAnimeInfo(): AnimeInfo {
    return AnimeInfo(
        id = this?.id ?: 0,
        idMal = this?.idMal ?: 0,
        title = this?.title?.romaji ?: this?.title?.english ?: "",
        imageUrl = this?.coverImage?.large,
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
            AnimHomeBannerInfo(
                animeInfo = it?.animeBasicInfo.toAnimeInfo(),
                description = it?.description ?: "",
                startDate = Util.convertToDateFormat(
                    it?.startDate?.year,
                    it?.startDate?.month,
                    it?.startDate?.day
                ),
                episode = it?.episodes ?: 0,
                genres = it?.genres?.take(2) ?: emptyList(),
                studioTitle = it?.studios?.nodes?.firstOrNull()?.name ?: ""
            )
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
            startDate = Util.convertToDateFormat(
                this?.startDate?.year,
                this?.startDate?.month,
                this?.startDate?.day
            ),
            endDate = Util.convertToDateFormat(
                this?.endDate?.year,
                this?.endDate?.month,
                this?.endDate?.day
            ),
            trailerInfo = TrailerInfo(
                id = this?.trailer?.id ?: "",
                thumbnailUrl = this?.trailer?.thumbnail
            ),
            type = this?.type?.rawValue ?: "Unknown",
            genres = this?.genres,
            tags = this?.tags?.filter { it != null && it.isAdult == false }?.map {
                TagInfo(
                    name = it?.name ?: "Unknown",
                    desc = it?.description,
                    ranking = it?.rank ?: 0,
                    isSpoiler = it?.isMediaSpoiler ?: false
                )
            },
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