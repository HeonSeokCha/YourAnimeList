package com.chs.youranimelist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.AnimeRecommendQuery
import com.chs.youranimelist.data.mapper.toAnimeInfoList
import com.chs.youranimelist.domain.model.AnimeInfo

class AnimeRecPagingSource(
    private val apolloClient: ApolloClient,
    private val animeId: Int
) : PagingSource<Int, AnimeInfo>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeInfo>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeInfo> {
        return try {
            val page = params.key ?: 1
            val response = apolloClient
                .query(
                    AnimeRecommendQuery(
                        Optional.present(animeId),
                        Optional.present(page)
                    )
                )
                .execute()
                .data
                ?.toAnimeInfoList()!!

            LoadResult.Page(
                data = response.list,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.pageInfo.hasNextPage) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}