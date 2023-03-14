package com.chs.domain.model

data class ListInfo<T>(
    val pageInfo: com.chs.domain.model.PageInfo,
    val list: List<T>
)
