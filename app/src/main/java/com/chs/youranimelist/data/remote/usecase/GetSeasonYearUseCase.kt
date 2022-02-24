package com.chs.youranimelist.data.remote.usecase

import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.AnimeListRepository
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSeasonYearUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    suspend operator fun invoke(
        page: Int,
        selectedSort: MediaSort,
        selectedSeason: MediaSeason,
        selectedYear: Int,
        selectGenre: String
    ): Flow<NetworkState<AnimeListQuery.Data>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(
                NetworkState.Success(
                    repository.getAnimeList(
                        page,
                        selectedSort,
                        selectedSeason,
                        selectedYear,
                        selectGenre
                    ).data!!
                )
            )

        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}