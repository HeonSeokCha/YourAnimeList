package com.chs.youranimelist.data.datasource

import com.chs.youranimelist.data.model.AnimeDetails

interface JikanService {
    suspend fun getAnimeTheme(malId: Int): AnimeDetails?
}