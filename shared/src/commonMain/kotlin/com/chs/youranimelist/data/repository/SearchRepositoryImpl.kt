package com.chs.youranimelist.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.chs.youranimelist.util.Constants
import com.chs.youranimelist.data.paging.SearchAnimePagingSource
import com.chs.youranimelist.data.paging.SearchCharacterPagingSource
import com.chs.youranimelist.data.source.db.dao.SearchListDao
import com.chs.youranimelist.data.source.db.entity.SearchHistoryEntity
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val dao: SearchListDao
) : SearchRepository {
    override fun getAnimeSearchResult(query: String): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                initialLoadSize = Constants.PAGING_SIZE * 3
            )
        ) {
            SearchAnimePagingSource(
                apolloClient = apolloClient,
                search = query
            )
        }.flow
    }

    override fun getCharacterSearchResult(name: String): Flow<PagingData<CharacterInfo>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                initialLoadSize = Constants.PAGING_SIZE * 3
            )
        ) {
            SearchCharacterPagingSource(
                apolloClient,
                search = name
            )
        }.flow
    }

    override fun getSearchHistory(): Flow<List<String>> {
        return dao.getSearchHistory()
    }

    override suspend fun insertSearchHistory(title: String) {
        dao.insert(
            SearchHistoryEntity(title = title)
        )
    }

    override suspend fun deleteSearchHistory(title: String) {
        dao.deleteHistory(
            SearchHistoryEntity(title)
        )
    }
}