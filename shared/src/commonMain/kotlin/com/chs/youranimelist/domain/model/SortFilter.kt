package com.chs.youranimelist.domain.model

data class SortFilter(
    val selectSort: List<SortType> = listOf(
        SortType.POPULARITY,
        SortType.AVERAGE
    ),
    val selectSeason: SeasonType? = null,
    val selectYear: Int? = null,
    val selectStatus: StatusType? = null,
    val selectGenre: List<String>? = null,
    val selectTags: List<String>? = null
)
