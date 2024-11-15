package com.chs.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.data.ActorAnimeListQuery
import com.chs.data.mapper.toAnimeInfo
import com.chs.data.type.MediaSort
import com.chs.domain.model.AnimeInfo

class VoiceActorAnimePagingSource(
    private val apolloClient: ApolloClient,
    private val voiceActorId: Int,
    private val sortOptions: List<MediaSort>
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
                ActorAnimeListQuery(
                    id = Optional.present(voiceActorId),
                    page = Optional.present(page),
                    sort = Optional.present(
                        listOf(MediaSort.SCORE_DESC)
                    )
                )
            ).execute()

            if (response.data == null) {
                return if (response.exception == null) {
                    LoadResult.Error(Exception(response.errors!!.first().message))
                } else {
                    LoadResult.Error(Exception(response.exception!!.message))
                }
            }

            val data = response.data!!.Staff?.staffMedia

            LoadResult.Page(
                data = data?.nodes?.map { it?.animeBasicInfo.toAnimeInfo() }
                    ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
