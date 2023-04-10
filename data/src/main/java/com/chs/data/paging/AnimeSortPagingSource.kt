package com.chs.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.AnimeListQuery
import com.chs.data.mapper.toAnimeInfo
import com.chs.domain.model.AnimeInfo
import com.chs.type.MediaSeason
import com.chs.type.MediaSort

class AnimeSortPagingSource(
    private val apolloClient: ApolloClient,
    private val sort: MediaSort,
    private val season: MediaSeason?,
    private val seasonYear: Int?,
    private val genre: String?
) : PagingSource<Int, AnimeInfo>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeInfo>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeInfo> {
        return try {
            Log.e("load", "${sort.rawValue}, ${season?.rawValue} $seasonYear $genre")
            val page = params.key ?: 1
            val response = apolloClient
                .query(
                    AnimeListQuery(
                        page = Optional.present(page),
                        sort = Optional.present(sort),
                        season = if (season == null) {
                            Optional.absent()
                        } else Optional.present(season),
                        seasonYear = if (seasonYear == null) {
                            Optional.absent()
                        } else Optional.present(seasonYear),
                        genre = if (genre == null) {
                            Optional.absent()
                        } else Optional.present(genre)
                    )
                )
                .execute()
                .data!!

            LoadResult.Page(
                data = response.page?.media?.map {
                    it?.toAnimeInfo()!!
                } ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.page?.pageInfo?.pageBasicInfo?.hasNextPage == true) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}