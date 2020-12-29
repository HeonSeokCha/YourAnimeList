package com.chs.youranimelist.network.dto

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("mal_id")
    val mal_id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String
)
