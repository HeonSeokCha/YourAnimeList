package com.chs.youranimelist.network.dto

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class AnimeDetail (
    @SerializedName("mal_id")
    val mal_id: String,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("trailer_url")
    val trailer_url: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("score")
    val score: String,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("genres")
    val genres: ArrayList<Genres>
    )

