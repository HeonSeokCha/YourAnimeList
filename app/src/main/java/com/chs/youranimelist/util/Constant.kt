package com.chs.youranimelist.util

import com.chs.type.MediaSeason
import com.chs.type.MediaSort
import com.chs.youranimelist.presentation.ui.theme.MediaStatusFinishedColor
import com.chs.youranimelist.presentation.ui.theme.MediaStatusNotYetColor
import com.chs.youranimelist.presentation.ui.theme.MediaStatusReleasingColor

object Constant {
    const val ANILIST_API_URL: String = "https://graphql.anilist.co/v2"
    const val JIKAN_API_URL: String = "https://api.jikan.moe/v4/anime"
    const val YOUTUBE_BASE_URL: String = "https://www.youtube.com/watch?v="

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
    const val TOP_ANIME: String = "Top Anime"
    const val NO_SEASON_NO_YEAR: String = "noSeasonNoYear"
    const val NO_SEASON: String = "noSeason"
    const val SEASON_YEAR: String = "seasonYear"

    val HOME_SORT_TILE = listOf(
        TRENDING_NOW,
        POPULAR_THIS_SEASON,
        UPCOMING_NEXT_SEASON,
        ALL_TIME_POPULAR,
        TOP_ANIME
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
        Pair("RELEASING", "Up Releasing" to MediaStatusReleasingColor),
        Pair("FINISHED", "FINISHED" to MediaStatusFinishedColor),
        Pair("NOT_YET_RELEASED", "Up Coming" to MediaStatusNotYetColor)
    )
}