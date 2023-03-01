package com.chs.youranimelist.domain.model

data class PageInfo(
    val currentPage: Int,
    val lasPage: Int,
    val hasNextPage: Boolean
)
