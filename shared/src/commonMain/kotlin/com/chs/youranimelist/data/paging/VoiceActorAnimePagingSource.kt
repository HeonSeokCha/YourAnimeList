package com.chs.youranimelist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.youranimelist.data.ActorMediaListQuery
import com.chs.youranimelist.data.mapper.toAnimeInfo
import com.chs.youranimelist.data.mapper.toCharacterInfo
import com.chs.youranimelist.data.type.MediaSort
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo

class VoiceActorAnimePagingSource(
    private val apolloClient: ApolloClient,
    private val voiceActorId: Int,
    private val sortOptions: List<MediaSort>
) : PagingSource<Int, Pair<CharacterInfo, AnimeInfo>>() {

    override fun getRefreshKey(state: PagingState<Int, Pair<CharacterInfo, AnimeInfo>>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pair<CharacterInfo, AnimeInfo>> {
        return try {
            val page = params.key ?: 1
            val response = apolloClient.query(
                ActorMediaListQuery(
                    id = Optional.present(voiceActorId),
                    page = Optional.present(page),
                    sort = Optional.present(sortOptions)
                )
            ).execute()

            if (response.data == null) {
                return if (response.exception == null) {
                    LoadResult.Error(Exception(response.errors!!.first().message))
                } else {
                    LoadResult.Error(Exception(response.exception!!.message))
                }
            }

            val data = response.data!!.Staff?.characterMedia


            LoadResult.Page(
                data = data?.edges
                    ?.filter { it?.node?.animeBasicInfo?.isAdult != true }
                    ?.map {
                        it?.characters?.first()?.characterBasicInfo.toCharacterInfo() to it?.node?.animeBasicInfo.toAnimeInfo()
                    } ?: emptyList(),
                prevKey = null,
                nextKey = if (data?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
