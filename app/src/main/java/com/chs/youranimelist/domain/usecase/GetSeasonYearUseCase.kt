package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
        selectGenre: String?
    ): Flow<NetworkState<AnimeListQuery.Page>> = flow {
        emit(NetworkState.Loading())

        repository.getAnimeList(
            page,
            selectedSort,
            selectedSeason,
            selectedYear,
            selectGenre
        ).catch { e ->
            emit(NetworkState.Error(e.message.toString()))
        }.collect {
            emit(NetworkState.Success(it.data!!.page!!))
        }
    }
}