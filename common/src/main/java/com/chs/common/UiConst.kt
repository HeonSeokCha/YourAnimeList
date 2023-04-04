package com.chs.common

import java.util.*
import kotlin.collections.HashMap

object UiConst {
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

    enum class Season(
        val rawValue: String
    ) {
        WINTER("WINTER"),
        SPRING("SPRING"),
        SUMMER("SUMMER"),
        FALL("FALL")
    }

    enum class SortType(
        val rawValue: String
    ) {
        POPULARITY("POPULARITY_DESC"),
        AVERAGE("SCORE_DESC"),
        FAVORITE("FAVOURITES_DESC"),
        NEWEST("START_DATE_DESC"),
        OLDEST("START_DATE"),
        TITLE("TITLE_ENGLISH_DESC"),
        TRENDING("TRENDING_DESC")
    }

    val HOME_SORT_TILE = listOf(
        "TRENDING NOW" to "",
        "POPULAR THIS SEASON" to "",
        "UPCOMING NEXT SEASON" to "",
        "ALL TIME POPULAR" to "",
        "TOP ANIME" to ""
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

    val filterMenuList = listOf(
        "Year",
        "Season",
        "Sort",
        "Genre"
    )

    val sortTypeList = SortType
        .values()
        .map { it.name to it.rawValue }

    val yearSortList = (Calendar.getInstance().get(Calendar.YEAR) downTo 1980)
        .map { it.toString() to it }

    val seasonFilterList = Season
        .values()
        .map { it.name to it.rawValue }

    val mediaStatus = hashMapOf(
        Pair("RELEASING", "Up Releasing" to 0xFF00BCD4),
        Pair("FINISHED", "FINISHED" to 0xFF4CAF50),
        Pair("NOT_YET_RELEASED", "Up Coming" to 0xFF673AB7)
    )
}