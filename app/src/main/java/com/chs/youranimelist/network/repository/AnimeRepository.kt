package com.chs.youranimelist.network.repository

import com.chs.youranimelist.network.dto.AnimeDetailResponse

interface AnimeRepository {
    suspend fun getAnime(animeId: String):List<AnimeDetailResponse>

    suspend fun searchAnime()
}