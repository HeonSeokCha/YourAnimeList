package com.chs.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.data.ActorCharaListQuery
import com.chs.data.mapper.toCharacterInfo
import com.chs.data.type.CharacterSort
import com.chs.domain.model.CharacterInfo

class VoiceActorCharaPagingSource(
    private val apolloClient: ApolloClient,
    private val voiceActorId: Int,
    private val sortOptions: List<CharacterSort>
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
                ActorCharaListQuery(
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

            val data = response.data!!.Staff?.characters

            LoadResult.Page(
                data = data?.nodes?.map { it?.characterBasicInfo.toCharacterInfo() }
                    ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
