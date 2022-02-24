package com.chs.youranimelist.data.remote.usecase

import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.AnimeListRepository
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoSeasonUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    suspend operator fun invoke(
        page: Int,
        selectedSort: MediaSort,
        selectedYear: Int,
        selectGenre: String
    ): Flow<NetworkState<NoSeasonQuery.Page>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(
                NetworkState.Success(
                    repository.getNoSeasonList(
                        page,
                        selectedSort,
                        selectedYear,
                        selectGenre
                    ).data!!.page!!
                )
            )

        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}