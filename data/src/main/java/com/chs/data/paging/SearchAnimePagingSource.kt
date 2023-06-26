package com.chs.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.SearchAnimeQuery
import com.chs.data.mapper.toAnimeInfo
import com.chs.domain.model.AnimeInfo

class SearchAnimePagingSource(
    private val apolloClient: ApolloClient,
    private val search: String
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
            val response = apolloClient.query(
                SearchAnimeQuery(
                    page = Optional.present(page),
                    search = Optional.present(search)
                )
            )
                .execute()
                .data!!
                .page


            LoadResult.Page(
                data = response?.media?.map { it?.animeBasicInfo.toAnimeInfo() }
                    ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}