package com.chs.youranimelist.domain.model

data class ListInfo<T>(
    val pageInfo: PageInfo,
    val list: List<T>
)
