package com.chs.youranimelist.data.remote.paging

import androidx.paging.*
import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator @Inject constructor(
    private val apollo: ApolloClient,
    private val query: NoSeasonQuery
) : PagingSource<Int, AnimeList>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeList> {
        val currentPage = params.key ?: 1
        val animeList = arrayListOf<AnimeList>()
        val response = apollo.query(query).execute().data!!.page!!.pageInfo
        val anime = apollo.query(query).execute().data!!.page!!.media!!.forEach {
            animeList.add(it!!.fragments.animeList)
        }

        return if (response.)
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeList>): Int? {
        return state.anchorPosition
    }

}