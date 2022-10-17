package com.chs.youranimelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.data.paging.SearchAnimePagingSource
import com.chs.youranimelist.data.paging.SearchCharacterPagingSource
import com.chs.youranimelist.data.paging.SearchMangaPagingSource
import com.chs.youranimelist.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : SearchRepository {

    override fun searchAnime(search: String): Flow<PagingData<SearchAnimeQuery.Medium>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchAnimePagingSource(
                apolloClient,
                search
            )
        }.flow
    }

    override fun searchManga(search: String): Flow<PagingData<SearchMangaQuery.Medium>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchMangaPagingSource(
                apolloClient,
                search
            )
        }.flow
    }

    override fun searchCharacter(search: String): Flow<PagingData<SearchCharacterQuery.Character>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchCharacterPagingSource(
                apolloClient,
                search
            )
        }.flow
    }

}