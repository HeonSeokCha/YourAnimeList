package com.chs.youranimelist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.youranimelist.data.AnimeRecommendQuery
import com.chs.youranimelist.data.mapper.toAnimeInfo
import com.chs.youranimelist.domain.model.AnimeInfo

class AnimeRecPagingSource(
    private val apolloClient: ApolloClient,
    private val animeId: Int
) : PagingSource<Int, AnimeInfo>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeInfo>): Int? = 0

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

            if (response.data == null) {
                return if (response.exception == null) {
                    LoadResult.Error(Exception(response.errors!!.first().message))
                } else {
                    LoadResult.Error(Exception(response.exception!!.message))
                }
            }

            val data = response.data!!
                .Media
                ?.recommendations

            LoadResult.Page(
                data = data?.nodes?.filter { it?.mediaRecommendation?.animeBasicInfo?.isAdult == false }
                    ?.map {
                        it?.mediaRecommendation?.animeBasicInfo.toAnimeInfo()
                    } ?: emptyList(),
                prevKey = null,
                nextKey = if (data?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}