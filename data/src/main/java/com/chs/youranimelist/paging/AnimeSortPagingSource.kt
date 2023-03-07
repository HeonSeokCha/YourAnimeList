package com.chs.youranimelist.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.NoSeasonNoYearQuery
import com.chs.youranimelist.NoSeasonQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Constant

class AnimeSortPagingSource(
    private val apolloClient: ApolloClient,
    private val selectType: String,
    private val sort: MediaSort,
    private val season: MediaSeason?,
    private val seasonYear: Int?,
    private val genre: String?
) : PagingSource<Int, AnimeList>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeList>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeList> {
        return try {
            val page = params.key ?: 1
            val animeList: ArrayList<AnimeList> = arrayListOf()
            var hasNextPage: Boolean = false
            when (selectType) {
                Constant.SEASON_YEAR -> {
                    val response = apolloClient.query(
                        AnimeListQuery(
                            page = page,
                            sort = sort,
                            season = season,
                            seasonYear = seasonYear,
                            genre = genre
                        )
                    ).execute().data
                    hasNextPage = response?.page?.pageInfo?.hasNextPage ?: false
                    response?.page?.media?.forEach { anime ->
                        animeList.add(anime?.animeList!!)
                    }
                }
                Constant.NO_SEASON -> {
                    val response = apolloClient.query(
                        NoSeasonQuery(
                            page = page,
                            sort = sort,
                            seasonYear = seasonYear,
                            genre = genre
                        )
                    ).execute().data
                    hasNextPage = response?.page?.pageInfo?.hasNextPage ?: false
                    response?.page?.media?.forEach { anime ->
                        animeList.add(anime?.animeList!!)
                    }
                }
                Constant.NO_SEASON_NO_YEAR -> {
                    val response = apolloClient.query(
                        NoSeasonNoYearQuery(
                            page = page,
                            sort = sort,
                            genre = genre
                        )
                    ).execute().data
                    hasNextPage = response?.page?.pageInfo?.hasNextPage ?: false
                    response?.page?.media?.forEach { anime ->
                        animeList.add(anime?.animeList!!)
                    }
                }
            }

            LoadResult.Page(
                data = animeList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (hasNextPage) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}