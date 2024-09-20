package com.chs.domain.model

data class SortFilter(
    val selectSort: String = "TRENDING_DESC",
    val selectSeason: String? = null,
    val selectYear: Int? = null,
    val selectGenre: List<String>? = null,
    val selectStatus: String? = null,
    val selectTags: List<String>? = null
)
