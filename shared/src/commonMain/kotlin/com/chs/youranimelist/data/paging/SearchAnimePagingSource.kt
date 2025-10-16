package com.chs.youranimelist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.youranimelist.data.SearchAnimeQuery
import com.chs.youranimelist.data.mapper.toAnimeInfo
import com.chs.youranimelist.domain.model.AnimeInfo

class SearchAnimePagingSource(
    private val apolloClient: ApolloClient,
    private val search: String
) : PagingSource<Int, AnimeInfo>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeInfo>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeInfo> {
        return try {
            val page = params.key ?: 1
            val response = apolloClient.query(
                SearchAnimeQuery(
                    page = Optional.present(page),
                    search = Optional.present(search)
                )
            ).execute()

            if (response.data == null) {
                return if (response.exception == null) {
                    LoadResult.Error(Exception(response.errors!!.first().message))
                } else {
                    LoadResult.Error(Exception(response.exception!!.message))
                }
            }

            val data = response.data!!.page

            LoadResult.Page(
                data = data?.media?.map { it?.animeBasicInfo.toAnimeInfo() }
                    ?: emptyList(),
                prevKey = null,
                nextKey = if (data?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}