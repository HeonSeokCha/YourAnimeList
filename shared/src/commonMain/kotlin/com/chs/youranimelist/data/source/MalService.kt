package com.chs.youranimelist.data.source

import com.chs.youranimelist.data.model.ResponseMalInfo
import com.chs.youranimelist.util.Constants
import com.chs.youranimelist.util.Constants.MAL_API_CLIENT_ID
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.*
import io.ktor.http.appendPathSegments

class MalService(private val client: HttpClient) {
    suspend fun getAnimeTheme(malId: Int): ResponseMalInfo {
        return client.get(Constants.MAL_API_URL) {
            this.headers.append("X-MAL-CLIENT-ID", MAL_API_CLIENT_ID)
            url {
                this.appendPathSegments("$malId")
                this.parameters.append("fields", "opening_themes,ending_themes")
            }
        }.body()
    }
}

class CustomHttpLogger : Logger {
    override fun log(message: String) {
    }
}