package com.chs.youranimelist.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.AnimeRecommendQuery

class AnimeRecPagingSource(
    private val apolloClient: ApolloClient,
    private val animeId: Int
) : PagingSource<Int, AnimeRecommendQuery.Edge>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeRecommendQuery.Edge>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeRecommendQuery.Edge> {
        return try {
            val page = params.key ?: 1
            val response = apolloClient.query(AnimeRecommendQuery(animeId, page)).execute().data

            LoadResult.Page(
                data = response?.animeRecommend?.recommendations?.edges!!.map { it!! },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.animeRecommend.recommendations.pageInfo?.hasNextPage!!) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}