package com.chs.youranimelist.model

data class ListInfo<T>(
    val pageInfo: com.chs.youranimelist.model.PageInfo,
    val list: List<T>
)
