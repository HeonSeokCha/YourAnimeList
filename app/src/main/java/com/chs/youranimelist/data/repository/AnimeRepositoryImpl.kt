package com.chs.youranimelist.data.repository

import com.apollographql.apollo3.ApolloClient
import com.chs.HomeAnimeListQuery
import com.chs.youranimelist.data.mapper.toAnimeRecommendList
import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.domain.repository.AnimeRepository

class AnimeRepositoryImpl(
    private val apolloClient: ApolloClient
) : AnimeRepository {
    override suspend fun getAnimeRecommendList(): AnimeRecommendList {
        return apolloClient
            .query(HomeAnimeListQuery())
            .execute()
            .data
            ?.toAnimeRecommendList()!!
    }

    override suspend fun getAnimeFilteredList(
        selectType: String,
        sortType: String,
        season: String,
        year: Int,
        genre: String?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeDetailInfo(animeId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeDetailInfoRecommendList(animeId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeDetailTheme(animeId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeSearchResult(title: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedAnimeList() {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedAnimeInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun insertSavedAnimeInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSavedAnimeInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeGenreList() {
        TODO("Not yet implemented")
    }
}