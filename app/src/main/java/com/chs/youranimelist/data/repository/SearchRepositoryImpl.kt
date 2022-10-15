package com.chs.youranimelist.data.repository

import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : SearchRepository {

    override fun searchAnime(search: String): Flow<PagingData<SearchAnimeQuery.Data>> {
        TODO("Not yet implemented")
    }

    override fun searchManga(search: String): Flow<PagingData<SearchMangaQuery.Data>> {
        TODO("Not yet implemented")
    }

    override fun searchCharacter(search: String): Flow<PagingData<SearchCharacterQuery.Data>> {
        TODO("Not yet implemented")
    }

}