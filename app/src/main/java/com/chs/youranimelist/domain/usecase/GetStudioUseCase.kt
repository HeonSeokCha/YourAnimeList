package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.StudioRepository
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStudioUseCase @Inject constructor(
    private val repository: StudioRepository
) {
    suspend operator fun invoke(
        studioId: Int,
        sort: MediaSort,
        page: Int
    ): Flow<NetworkState<StudioAnimeQuery.Studio>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(
                NetworkState.Success(
                    repository.getStudioAnime(studioId, sort, page).data?.studio!!
                )
            )

        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}