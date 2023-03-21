package com.chs.data.repository


import com.chs.domain.repository.MangaRepository

class MangaRepositoryImpl : MangaRepository {
    override suspend fun getMangaDetailInfo(mangaId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getMangaSearchResult(title: String) {
        TODO("Not yet implemented")
    }

    override fun getSavedMangaList() {
        TODO("Not yet implemented")
    }

    override fun getSavedMangaInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun insertSavedMangaInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSavedMangaInfo() {
        TODO("Not yet implemented")
    }
}