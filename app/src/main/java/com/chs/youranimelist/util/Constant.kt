package com.chs.youranimelist.util

import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.type.MediaStatus

object Constant {
    const val ANILIST_API_URL: String = "https://graphql.anilist.co"
    const val JIKAN_API_URL: String = "https://api.jikan.moe/v3/anime"

    const val TARGET_MAIN: String = "main"
    const val TARGET_SORT: String = "sort"
    const val TARGET_MEDIA: String = "media"
    const val TARGET_ANIME: String = "anime"
    const val TARGET_MANGA: String = "manga"
    const val TARGET_CHARA: String = "chara"
    const val TARGET_SEARCH: String = "searchType"
    const val TARGET_ID: String = "id"
    const val TARGET_ID_MAL: String = "idMal"
    const val TARGET_SORTED: String = "sorted"
    const val TARGET_TYPE: String = "type"
    const val TARGET_GENRE: String = "genre"
    const val TARGET_SEASON: String = "season"
    const val TRENDING_NOW: String = "TRENDING NOW"
    const val POPULAR_THIS_SEASON: String = "POPULAR THIS SEASON"
    const val UPCOMING_NEXT_SEASON: String = "UPCOMING NEXT SEASON"
    const val ALL_TIME_POPULAR: String = "ALL TIME POPULAR"
    const val NO_SEASON_NO_YEAR: String = "noSeasonNoYear"
    const val NO_SEASON: String = "noSeason"
    const val SEASON_YEAR: String = "seasonYear"

    val HOME_SORT_TILE = listOf(
        TRENDING_NOW,
        POPULAR_THIS_SEASON,
        UPCOMING_NEXT_SEASON,
        ALL_TIME_POPULAR,
    )


    val GENRE_COLOR = hashMapOf(
        Pair("Action", "#24687B"),
        Pair("Adventure", "#014037"),
        Pair("Comedy", "#E6977E"),
        Pair("Drama", "#7E1416"),
        Pair("Ecchi", "#7E174A"),
        Pair("Fantasy", "#989D60"),
        Pair("Hentai", "#37286B"),
        Pair("Horror", "#5B1765"),
        Pair("Mahou Shoujo", "#BF5264"),
        Pair("Mecha", "#542437"),
        Pair("Music", "#329669"),
        Pair("Mystery", "#3D3251"),
        Pair("Psychological", "#D85C43"),
        Pair("Romance", "#C02944"),
        Pair("Sci-Fi", "#85B14B"),
        Pair("Slice of Life", "#D3B042"),
        Pair("Sports", "#6B9145"),
        Pair("Supernatural", "#338074"),
        Pair("Thriller", "#224C80")
    )

    val EXTERNAL_LINK = hashMapOf(
        Pair("anilist", "#324760"),
        Pair("twitter", "#03A9F4"),
        Pair("crunchyroll", "#FF9100"),
        Pair("youtube", "#FF0000"),
        Pair("funimation", "#452C8A"),
        Pair("hidive", "#03A8EB"),
        Pair("vrv", "#FEDD01"),
        Pair("netflix", "#F44335"),
        Pair("amazon", "#04A3DD"),
        Pair("hulu", "#8AC34A"),
        Pair("hbo max", "#9531EC"),
        Pair("animelab", "#3B0087"),
        Pair("viz", "#FF0000"),
        Pair("adult swim", "#171717"),
        Pair("retro crush", "#000000"),
        Pair("midnight pulp", "#B7F00F"),
        Pair("tubi tv", "#F84C18"),
        Pair("contv", "#E35623"),
        Pair("manga plus", "#DC0812"),
        Pair("manga.club", "#F47D30"),
        Pair("fakku", "#911918"),
        Pair("webtoons", "#04CF62"),
        Pair("lezhin", "#E71D31"),
        Pair("toomics", "#EB2C2C"),
        Pair("web comics", "#F7745E"),
        Pair("comicwalker (jp)", "#F80003"),
        Pair("pixiv comic (jp)", "#088ED5"),
        Pair("comico (jp)", "#EE0208"),
        Pair("mangabox (jp)", "#3999B8"),
        Pair("pixiv novel (jp)", "#088ED5"),
        Pair("piccoma (jp)", "#F3C016"),
        Pair("pocket magazine (jp)", "#0C2F89"),
        Pair("nico nico seiga (jp)", "#323232"),
        Pair("shonen jump plus (jp)", "#E60109"),
        Pair("naver (ko)", "#00CE63"),
        Pair("daum webtoon (ko)", "#F82E40"),
        Pair("bomtoon (ko)", "#F82BA8"),
        Pair("kakaopage (ko)", "#F8CD01"),
        Pair("kuaikan manhua (cn)", "#F8D00B"),
        Pair("qq (cn)", "#FB9144"),
        Pair("dajiaochong manhua (cn)", "#E9CE0E"),
        Pair("weibo manhua (cn)", "#E0172B"),
        Pair("manman manhua (cn)", "#FF5746")
    )

    val animeSortArray = listOf(
        "POPULARITY",
        "AVERAGE SCORE",
        "FAVORITE",
        "NEWEST",
        "OLDEST",
        "TITLE",
    )

    val animeSeasonList = arrayListOf(
        MediaSeason.WINTER,
        MediaSeason.SPRING,
        MediaSeason.SUMMER,
        MediaSeason.FALL
    )

    val animeSortList = arrayListOf(
        MediaSort.POPULARITY_DESC,
        MediaSort.SCORE_DESC,
        MediaSort.FAVOURITES_DESC,
        MediaSort.START_DATE_DESC,
        MediaSort.START_DATE,
        MediaSort.TITLE_ENGLISH_DESC,
    )

    val mediaStatus = hashMapOf(
        Pair(MediaStatus.RELEASING, "Up Releasing"),
        Pair(MediaStatus.FINISHED, "FINISHED"),
        Pair(MediaStatus.NOT_YET_RELEASED, "Up Coming")
    )

    val mediaStatusColor = hashMapOf(
        Pair(MediaStatus.RELEASING, 0xFF4CAF50),
        Pair(MediaStatus.FINISHED, 0xFF00BCD4),
        Pair(MediaStatus.NOT_YET_RELEASED, 0xFF673AB7)
    )
}