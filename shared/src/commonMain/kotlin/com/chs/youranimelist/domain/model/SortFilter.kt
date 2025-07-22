package com.chs.youranimelist.domain.model

data class SortFilter(
    val selectSort: List<String> = listOf(
        "POPULARITY_DESC",
        "SCORE_DESC"
    ),
    val selectSeason: String? = null,
    val selectYear: Int? = null,
    val selectStatus: String? = null,
    val selectGenre: List<String>? = null,
    val selectTags: List<String>? = null
)
