package com.chs.youranimelist.network.services

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.chs.youranimelist.BuildConfig
import com.chs.youranimelist.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


object ApolloServices {
    private val cache = LruNormalizedCacheFactory(
        EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build()
    )

    val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(Constant.ANILIST_API_URL)
//        .okHttpClient(createOkHttpClient())
        .normalizedCache(cache)
        .build()

//    private fun createOkHttpClient(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        if (BuildConfig.DEBUG) {
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            interceptor.level = HttpLoggingInterceptor.Level.NONE
//        }
//        return OkHttpClient.Builder()
//            .connectTimeout(20, TimeUnit.SECONDS)
//            .readTimeout(20, TimeUnit.SECONDS)
//            .writeTimeout(20, TimeUnit.SECONDS)
//            .addNetworkInterceptor(interceptor)
//            .build()
//    }
}