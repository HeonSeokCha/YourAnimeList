package com.chs.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.SearchCharacterQuery
import com.chs.domain.model.CharacterInfo
import com.chs.data.mapper.toCharacterInfo

class SearchCharacterPagingSource(
    private val apolloClient: ApolloClient,
    private val search: String
) : PagingSource<Int, CharacterInfo>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterInfo>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterInfo> {
        return try {
            val page = params.key ?: 1
            val response = apolloClient.query(
                SearchCharacterQuery(
                    page = Optional.present(page),
                    search = Optional.present(search)
                )
            ).execute()
                .data!!
                .page

            LoadResult.Page(
                data = response?.characters?.map { it?.characterBasicInfo?.toCharacterInfo()!! }!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}