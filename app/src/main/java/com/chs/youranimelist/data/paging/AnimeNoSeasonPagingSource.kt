package com.chs.youranimelist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSort

class AnimeNoSeasonPagingSource(
    private val apolloClient: ApolloClient,
    private val sort: MediaSort,
    private val seasonYear: Int,
    private val genre: String?
) : PagingSource<Int, NoSeasonQuery.Medium>() {

    override fun getRefreshKey(state: PagingState<Int, NoSeasonQuery.Medium>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoSeasonQuery.Medium> {
        return try {
            val page = params.key ?: 1
            val response = apolloClient.query(
                NoSeasonQuery(
                    page = page,
                    sort = sort,
                    seasonYear = seasonYear,
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