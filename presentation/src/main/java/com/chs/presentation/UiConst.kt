package com.chs.presentation

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.em
import java.util.*

object UiConst {
    const val TARGET_MEDIA: String = "media"
    const val TARGET_ANIME: String = "anime"
    const val TARGET_CHARA: String = "chara"
    const val TARGET_ID: String = "id"
    const val TARGET_ID_MAL: String = "idMal"
    const val TARGET_TYPE: String = "type"
    const val AVERAGE_SCORE_ID: String = "averageScoreId"
    const val FAVOURITE_ID: String = "favoriteId"
    const val TITLE_PREVIEW: String = "Title PreView"
    const val UNKNOWN: String = "Unknown"
    const val KEY_SEASON: String = "season"
    const val KEY_YEAR: String = "year"
    const val KEY_SORT: String = "sortOption"
    const val KEY_GENRE: String = "genre"
    const val INFINITE_PAGER_COUNT = Int.MAX_VALUE
    const val PAGER_CHANGE_DELAY = 5000L
    const val MAX_BANNER_SIZE: Int = 5
    const val BANNER_SIZE: Int = 6
    const val TEXT_UNKNOWN_ERROR: String = "Unknown Error.."

    val ANIME_DETAIL_TAB_LIST: List<String> = listOf(
        "OVERVIEW",
        "CHARACTER",
        "RECOMMEND"
    )

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


    val animeCategorySortList: List<Pair<String, Triple<SortType, Int, String?>>> =
        listOf(
            "TRENDING NOW" to Triple(
                SortType.TRENDING,
                0,
                null
            ),
            "POPULAR THIS SEASON" to Triple(
                SortType.POPULARITY,
                Util.getCurrentYear(),
                Util.getCurrentSeason()
            ),
            "UPCOMING NEXT SEASON" to Triple(
                SortType.POPULARITY,
                Util.getVariationYear(true),
                Util.getNextSeason()
            ),
            "ALL TIME POPULAR" to Triple(
                SortType.POPULARITY,
                0,
                null
            ),
            "TOP ANIME" to Triple(
                SortType.AVERAGE,
                0,
                null
            )
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

    val sortTypeList = SortType.entries

    val yearSortList = (Util.getVariationYear(true) downTo 1980)
        .map { it.toString() to it.toString() }

    val seasonFilterList = Season.entries

    val mediaStatus = hashMapOf(
        Pair("RELEASING", "Up Releasing" to 0xFF00BCD4),
        Pair("FINISHED", "FINISHED" to 0xFF4CAF50),
        Pair("NOT_YET_RELEASED", "Up Coming" to 0xFF673AB7)
    )


    val inlineContent = mapOf(
        Pair(
            AVERAGE_SCORE_ID,
            inlineContentBuilder(
                Icons.Rounded.Star,
                Color.Yellow
            )
        ),
        Pair(
            FAVOURITE_ID,
            inlineContentBuilder(
                Icons.Rounded.Favorite,
                Color.Red
            )
        )
    )

    private fun inlineContentBuilder(
        icon: ImageVector,
        tint: Color
    ): InlineTextContent {
        return InlineTextContent(
            Placeholder(
                width = 1.5.em,
                height = 1.5.em,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
            )
        }
    }
}