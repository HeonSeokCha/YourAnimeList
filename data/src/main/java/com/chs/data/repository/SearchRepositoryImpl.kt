package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.chs.data.paging.SearchAnimePagingSource
import com.chs.data.paging.SearchCharacterPagingSource
import com.chs.data.source.KtorJikanService
import com.chs.data.source.db.dao.SearchListDao
import com.chs.data.source.db.model.SearchHistoryEntity
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val jikanService: KtorJikanService,
    private val dao: SearchListDao
) : SearchRepository {
    override fun getAnimeSearchResult(query: String): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchAnimePagingSource(
                apolloClient = apolloClient,
                search = query
            )
        }.flow
    }

    override fun getCharacterSearchResult(name: String): Flow<PagingData<CharacterInfo>> {
        return Pager(
            PagingConfig(pageSize = 10)
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
        TODO("Not yet implemented")
    }
}