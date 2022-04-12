package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoSeasonNoYearUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    operator fun invoke(
        page: Int,
        selectedSort: MediaSort,
        selectGenre: String?
    ): Flow<NetworkState<NoSeasonNoYearQuery.Page>> = flow {
        emit(NetworkState.Loading())
        repository.getNoSeasonNoYearList(
            page,
            selectedSort,
            selectGenre
        ).catch { e ->
            emit(NetworkState.Error(e.message.toString()))
        }.collect {
            emit(NetworkState.Success(it.data!!.page!!))
        }
    }
}