package com.chs.youranimelist.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SeasonType(
    val rawValue: String
) {
    WINTER("WINTER"),
    SPRING("SPRING"),
    SUMMER("SUMMER"),
    FALL("FALL")
}
