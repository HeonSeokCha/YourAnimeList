package com.chs.youranimelist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.NoSeasonNoYearQuery
import com.chs.youranimelist.type.MediaSort

class AnimeNoSeasonNoYearPagingSource(
    private val apolloClient: ApolloClient,
    private val sort: MediaSort,
    private val genre: String?
) : PagingSource<Int, NoSeasonNoYearQuery.Medium>() {

    override fun getRefreshKey(state: PagingState<Int, NoSeasonNoYearQuery.Medium>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoSeasonNoYearQuery.Medium> {
        return try {
            val page = params.key ?: 1
            val response = apolloClient.query(
                NoSeasonNoYearQuery(
                    page = page,
                    sort = sort,
                    genre = genre
                )
            ).execute().data

            LoadResult.Page(
                data = response?.page?.media?.map { it!! }!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.page.pageInfo?.hasNextPage!!) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}