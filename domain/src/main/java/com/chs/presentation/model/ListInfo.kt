package com.chs.presentation.model

data class ListInfo<T>(
    val pageInfo: com.chs.presentation.model.PageInfo,
    val list: List<T>
)
