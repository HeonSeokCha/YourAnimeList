package com.chs.presentation

import androidx.compose.ui.graphics.Color

val String.color
    get() = Color(android.graphics.Color.parseColor(this))


val Int?.isNotEmptyValue
    get() = this != null || this != 0

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

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