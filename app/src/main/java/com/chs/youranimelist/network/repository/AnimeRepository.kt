package com.chs.youranimelist.network.repository

import com.chs.youranimelist.network.dto.Anime

interface AnimeRepository {
    suspend fun getAnime(animeId: String):List<Anime>

    suspend fun searchAnime()
}