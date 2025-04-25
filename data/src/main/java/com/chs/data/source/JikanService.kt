package com.chs.data.source

import android.util.Log
import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import com.chs.data.model.JikanAnimeDataDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JikanService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getAnimeTheme(malId: Int): JikanAnimeDataDto {
        return client.get("$malId/themes").body()
    }
}

class CustomHttpLogger : Logger {
    override fun log(message: String) {
        Log.d("loggerTag", message)
    }
}