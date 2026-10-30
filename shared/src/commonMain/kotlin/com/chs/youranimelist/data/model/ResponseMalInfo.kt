package com.chs.youranimelist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMalInfo(
    @SerialName("opening_themes")
    val openingTheme: List<ResponseMalThemeInfo>,
    @SerialName("ending_themes")
    val endingTheme: List<ResponseMalThemeInfo>
)