package com.chs.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.data.AnimeRecommendQuery
import com.chs.data.mapper.toAnimeInfo
import com.chs.domain.model.AnimeInfo

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

            if (response.hasErrors()) {
                return LoadResult.Error(Exception(response.errors!!.first().message))
            }

            val data = response.data!!
                .Media
                ?.recommendations

            LoadResult.Page(
                data = data?.nodes?.filter { it?.mediaRecommendation?.animeBasicInfo?.isAdult == false }
                    ?.map {
                        it?.mediaRecommendation?.animeBasicInfo.toAnimeInfo()
                    } ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}