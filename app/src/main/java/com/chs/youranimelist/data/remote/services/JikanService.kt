package com.chs.youranimelist.data.remote.services

import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.dto.AnimeDetails

interface JikanService {
    suspend fun getAnimeTheme(malId: Int): AnimeDetails?
}