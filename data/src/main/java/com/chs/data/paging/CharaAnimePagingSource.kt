package com.chs.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.CharacterDetailAnimeListQuery
import com.chs.data.mapper.toAnimeInfo
import com.chs.domain.model.AnimeInfo
import com.chs.type.MediaSort

class CharaAnimePagingSource(
    private val apolloClient: ApolloClient,
    private val charaId: Int,
    private val sort: MediaSort
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
                    CharacterDetailAnimeListQuery(
                        id = Optional.present(charaId),
                        page = Optional.present(page),
                        sort = Optional.present(sort)
                    )
                )
                .execute()
                .data!!

            LoadResult.Page(
                data = response.character?.media?.nodes?.filter { it?.animeBasicInfo?.isAdult == false }
                    ?.map {
                        it?.animeBasicInfo.toAnimeInfo()
                    } ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.character?.media?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1
                else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}