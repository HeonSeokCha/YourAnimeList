package com.chs.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient

class AnimeSortPagingSource(
    private val apolloClient: ApolloClient,
    private val selectType: String,
    private val sort: MediaSort,
    private val season: MediaSeason?,
    private val seasonYear: Int?,
    private val genre: String?
) : PagingSource<Int, AnimeList>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeList>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeList> {
        return try {
            val page = params.key ?: 1
            var hasNextPage: Boolean = false


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}