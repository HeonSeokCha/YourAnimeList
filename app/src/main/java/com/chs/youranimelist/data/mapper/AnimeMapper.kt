package com.chs.youranimelist.data.mapper

import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.domain.model.Anime

fun Anime.toAnimeDto(): AnimeDto {
    return AnimeDto(
        animeId = animeId,
        idMal = idMal,
        title = title,
        format = format,
        seasonYear = seasonYear,
        episode = episode ?: 0,
        coverImage = coverImage ?: "",
        averageScore = averageScore ?: 0,
        favorites = favorites ?: 0,
        studio = studio ?: "",
        genre = genre ?: emptyList(),
    )
}