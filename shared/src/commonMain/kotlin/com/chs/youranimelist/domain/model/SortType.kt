package com.chs.youranimelist.domain.model

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
