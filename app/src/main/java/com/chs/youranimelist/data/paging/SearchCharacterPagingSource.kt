package com.chs.youranimelist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.SearchCharacterQuery

class SearchCharacterPagingSource(
    private val apolloClient: ApolloClient,
    private val search: String
) : PagingSource<Int, SearchCharacterQuery.Character>() {

    override fun getRefreshKey(state: PagingState<Int, SearchCharacterQuery.Character>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchCharacterQuery.Character> {
        return try {
            val page = params.key ?: 1
            val response = apolloClient.query(
                SearchCharacterQuery(
                    page = page,
                    search = search
                )
            ).execute().data

            LoadResult.Page(
                data = response?.page?.characters?.map { it!! }!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.page.pageInfo?.hasNextPage!!) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}