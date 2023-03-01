package com.chs.youranimelist.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.AnimeDetailInfoQuery
import com.chs.HomeAnimeListQuery
import com.chs.youranimelist.data.ConvertDate
import com.chs.youranimelist.data.mapper.toAnimeDetailInfo
import com.chs.youranimelist.data.mapper.toAnimeRecommendList
import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.domain.model.AnimeThemeInfo
import com.chs.youranimelist.domain.repository.AnimeRepository

class AnimeRepositoryImpl(
    private val apolloClient: ApolloClient
) : AnimeRepository {
    override suspend fun getAnimeRecommendList(): AnimeRecommendList {
        return apolloClient
            .query(
                HomeAnimeListQuery(
                    currentSeason = Optional.present(ConvertDate.getCurrentSeason()),
                    nextSeason = Optional.present(ConvertDate.getNextSeason()),
                    currentYear = Optional.present(ConvertDate.getCurrentYear()),
                    nextYear = Optional.present(ConvertDate.getCurrentYear() + 1),
                    lastYear = Optional.present(ConvertDate.getCurrentYear() - 1)
                )
            )
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
    ): List<AnimeInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeDetailInfo(animeId: Int): AnimeDetailInfo {
        return apolloClient
            .query(
                AnimeDetailInfoQuery(
                    id = Optional.present(animeId)
                )
            )
            .execute()
            .data
            ?.toAnimeDetailInfo()!!
    }

    override suspend fun getAnimeDetailInfoRecommendList(animeId: Int): List<AnimeInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeDetailTheme(animeId: Int): AnimeThemeInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeSearchResult(title: String): List<AnimeInfo> {
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