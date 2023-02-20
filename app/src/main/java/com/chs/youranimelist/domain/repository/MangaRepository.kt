package com.chs.youranimelist.domain.repository

interface MangaRepository {

    suspend fun getMangaDetailInfo(mangaId: Int)

    suspend fun getMangaSearchResult(title: String)

    fun getSavedMangaList()

    fun getSavedMangaInfo()

    suspend fun insertSavedMangaInfo()

    suspend fun deleteSavedMangaInfo()
}