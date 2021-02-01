package com.chs.youranimelist.network.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.chs.youranimelist.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


object AnimeService {
    private const val BASE_URL = "https://graphql.anilist.co"

    private val cache = LruNormalizedCacheFactory(
        EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build())

    val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(BASE_URL)
        .normalizedCache(cache)
        .build()

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectTimeout(20,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }
}