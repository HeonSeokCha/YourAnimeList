package com.chs.domain.model

data class TagInfo(
    val name: String,
    val desc: String?,
    val ranking: Int,
    val isSpoiler: Boolean
)