package com.chs.youranimelist.domain.repository

interface AnimeRepository {

    suspend fun getAnimeRecommendList()

    suspend fun getAnimeFilteredList(
        selectType: String,
        sortType: String,
        season: String,
        year: Int,
        genre: String?
    )

    suspend fun getAnimeDetailInfo(animeId: Int)

    suspend fun getAnimeDetailInfoRecommendList(animeId: Int)

    suspend fun getAnimeDetailTheme(animeId: Int)

    suspend fun getAnimeSearchResult(title: String)

    suspend fun getSavedAnimeList()

    suspend fun getSavedAnimeInfo()

    suspend fun insertSavedAnimeInfo()

    suspend fun deleteSavedAnimeInfo()

    suspend fun getAnimeGenreList()

}