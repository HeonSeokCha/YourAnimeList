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
import com.chs.domain.model.SortFilter
import com.chs.presentation.main.Screen
import java.util.*

object UiConst {
    const val TARGET_MEDIA: String = "media"
    const val TARGET_CHARA: String = "chara"
    const val TARGET_ID: String = "id"
    const val TARGET_ID_MAL: String = "idMal"
    const val TARGET_TYPE: String = "type"
    const val AVERAGE_SCORE_ID: String = "averageScoreId"
    const val FAVOURITE_ID: String = "favoriteId"
    const val TITLE_PREVIEW: String = "Title PreView"
    const val UNKNOWN: String = "Unknown"
    const val KEY_SEASON: String = "season"
    const val MAX_BANNER_SIZE: Int = 5
    const val BANNER_SIZE: Int = 6
    const val TEXT_UNKNOWN_ERROR: String = "Unknown Error.."

    val SEARCH_TAB_LIST = listOf("ANIME", "CHARACTER")

    val ANIME_DETAIL_TAB_LIST: List<String> = listOf(
        "OVERVIEW",
        "CHARACTER",
        "RECOMMEND"
    )

    val ACTOR_DETAIL_TAB_LIST: List<String> = listOf(
        "OVERVIEW",
        "MEDIA",
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


    val animeCategorySortList: List<Pair<String, Screen.SortList>> =
        listOf(
            "TRENDING NOW" to Screen.SortList(
                sortOption = listOf(
                    SortType.TRENDING.rawValue,
                    SortType.POPULARITY.rawValue
                )
            ),
            "POPULAR THIS SEASON" to Screen.SortList(
                year = Util.getCurrentYear(),
                season = Util.getCurrentSeason()
            ),
            "UPCOMING NEXT SEASON" to Screen.SortList(
                year = Util.getVariationYear(),
                season = Util.getNextSeason()
            ),
            "ALL TIME POPULAR" to Screen.SortList(
                sortOption = listOf(
                    SortType.POPULARITY.rawValue
                )
            ),
            "TOP ANIME" to Screen.SortList(
                sortOption = listOf(
                    SortType.AVERAGE.rawValue
                )
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

    val sortTypeList = SortType.entries.map { it.name to it.rawValue }

    val yearSortList: List<Pair<String, String>> = (Util.getVariationYear() downTo 1980)
        .map { Pair(it.toString(), it.toString()) }

    val seasonFilterList = Season.entries.map { it.name to it.rawValue }

    val mediaStatus = hashMapOf(
        Pair("RELEASING", "Up Releasing" to 0xFF00BCD4),
        Pair("FINISHED", "FINISHED" to 0xFF4CAF50),
        Pair("NOT_YET_RELEASED", "Up Coming" to 0xFF673AB7)
    )

    val mediaStatusSortList: List<Pair<String, String>> = mediaStatus.map {
        it.value.first to it.key
    }

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