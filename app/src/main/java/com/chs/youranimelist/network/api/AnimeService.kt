package com.chs.youranimelist.network.api

import com.apollographql.apollo.ApolloClient



object AnimeService {
    private const val BASE_URL = "https://graphql.anilist.co"
    val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(BASE_URL)
        .build()
}