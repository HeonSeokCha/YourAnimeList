package com.chs.youranimelist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMalThemeInfo(
    @SerialName("text")
    val text: String
)
