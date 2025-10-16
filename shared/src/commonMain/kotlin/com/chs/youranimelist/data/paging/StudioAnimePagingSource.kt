package com.chs.youranimelist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.youranimelist.data.StudioAnimeQuery
import com.chs.youranimelist.data.mapper.toAnimeInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.data.type.MediaSort

class StudioAnimePagingSource(
    private val apolloClient: ApolloClient,
    private val studioId: Int,
    private val sort: MediaSort
) : PagingSource<Int, AnimeInfo>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeInfo>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeInfo> {
        return try {
            val page = params.key ?: 1
            val response = apolloClient
                .query(
                    StudioAnimeQuery(
                        id = Optional.present(studioId),
                        page = Optional.present(page),
                        sort = Optional.present(sort),
                    )
                )
                .execute()

            if (response.data == null) {
                return if (response.exception == null) {
                    LoadResult.Error(Exception(response.errors!!.first().message))
                } else {
                    LoadResult.Error(Exception(response.exception!!.message))
                }
            }

            val data = response
                .data!!
                .studio
                ?.media

            LoadResult.Page(
                data = data?.nodes?.map {
                    it?.animeBasicInfo.toAnimeInfo()
                } ?: emptyList(),
                prevKey = null,
                nextKey = if (data?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}



