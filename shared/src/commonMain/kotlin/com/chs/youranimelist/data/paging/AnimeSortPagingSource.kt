package com.chs.youranimelist.data.paging

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.youranimelist.data.AnimeListQuery
import com.chs.youranimelist.data.mapper.toAnimeInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.data.type.MediaSeason
import com.chs.youranimelist.data.type.MediaSort
import com.chs.youranimelist.data.type.MediaStatus

class AnimeSortPagingSource(
    private val apolloClient: ApolloClient,
    private val sort: List<MediaSort>,
    private val season: MediaSeason?,
    private val seasonYear: Int?,
    private val genres: List<String>?,
    private val tags: List<String>?,
    private val status: MediaStatus?
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
                    AnimeListQuery(
                        page = Optional.present(page),
                        sort = Optional.present(sort),
                        season = Optional.presentIfNotNull(season),
                        seasonYear = Optional.presentIfNotNull(seasonYear),
                        genre = Optional.presentIfNotNull(genres),
                        tags = Optional.presentIfNotNull(tags),
                        stauts = Optional.presentIfNotNull(status)
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
                .page

            LoadResult.Page(
                data = data?.media?.filter { it?.animeBasicInfo?.isAdult == false }?.map {
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