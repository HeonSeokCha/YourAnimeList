package com.chs.youranimelist.data.source

import android.util.Log
import com.chs.youranimelist.data.model.AnimeDetailDto
import com.chs.youranimelist.util.Constant
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorJikanService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getAnimeTheme(malId: Int): AnimeDetailDto? {
        val a = client.get("${Constant.JIKAN_API_URL}/$malId/themes") {
            headers.append("Content-Type", "application/json")
        }
        Log.e("Services$malId", a.body())
        return a.body()
    }
}