package com.chs.youranimelist.util

import com.chs.youranimelist.BuildKonfig

object Constants {
    const val ANILIST_API_URL: String = "https://graphql.anilist.co/v2"
    const val MAL_API_URL: String = "https://api.myanimelist.net/v2/anime"
    const val YOUTUBE_BASE_URL: String = "https://www.youtube.com/watch?v="

    const val PAGING_SIZE: Int = 3
    val MAL_API_CLIENT_ID: String = BuildKonfig.API_ACCESS_KEY
}