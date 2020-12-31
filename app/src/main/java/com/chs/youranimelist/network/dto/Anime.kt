package com.chs.youranimelist.network.dto

import com.google.gson.annotations.SerializedName

data class Anime (
    @SerializedName("id")
    val id: String,
    @SerializedName("idMal")
    val idMal: String,
    @SerializedName("title")
    val title: Title,
    @SerializedName("description")
    val description: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("trailer")
    val trailer: Trailer,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("coverImage")
    val coverImage: CoverImage,
    @SerializedName("bannerImage")
    val bannerImage: String,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("status")
    val status: String,
    @SerializedName("seasonYear")
    val seasonYear: String,
    @SerializedName("popularity")
    val popularity: String,
    @SerializedName("favourites")
    val favourites: String,
    @SerializedName("studios")
    val studios: Studios,
    @SerializedName("source")
    val source: String,
    @SerializedName("recommendations")
    val recommendations: Recommendations,

)

data class Title (
    @SerializedName("romaji")
    val romaji: String,
    @SerializedName("english")
    val english: String,
    @SerializedName("native")
    val native: String
)

data class Trailer (
    @SerializedName("id")
    val id: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?
)

data class CoverImage (
    @SerializedName("extraLarge")
    val extraLarge: String,
    @SerializedName("color")
    val color: String
)

data class Recommendations (
    @SerializedName("node")
    val recommendationsNode: List<AnimeRecommendation>
)

data class AnimeRecommendation (
    @SerializedName("id")
    val id: String,
    @SerializedName("idMal")
    val idMal: String,
    @SerializedName("title")
    val title:Title,
    @SerializedName("coverImage")
    val coverImage: CoverImage
)

data class Studios (
    @SerializedName("node")
    val studiosNode: List<StudioConnection>
)

data class StudioConnection (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

