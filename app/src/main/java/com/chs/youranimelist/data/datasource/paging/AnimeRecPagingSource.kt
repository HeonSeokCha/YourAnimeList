package com.chs.youranimelist.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery

class AnimeRecPagingSource(
    private val apollo: ApolloClient,
    private val animeId: Int
) : PagingSource<Int, AnimeRecommendQuery.Edge>() {
    override fun getRefreshKey(state: PagingState<Int, AnimeRecommendQuery.Edge>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeRecommendQuery.Edge> {
        return try {
            val page = params.key ?: 1
            val response = apollo.query(AnimeRecommendQuery(animeId, page)).execute().data

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