package com.chs.youranimelist.network.dto

import com.google.gson.annotations.SerializedName

data class AnimeSearchResponse(
    @SerializedName("mal_id")
    val mal_id: String,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("title")
    val title: String
)
