package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.data.model.AnimeDetails
import com.chs.youranimelist.domain.repository.AnimeRepository
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeThemeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(malId: Int): Flow<NetworkState<AnimeDetails?>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(repository.getAnimeOverviewTheme(malId)))
        } catch (e: RedirectResponseException) {
            // 3xx
            emit(NetworkState.Error(message = e.response.status.description))
        } catch (e: ClientRequestException) {
            // 4xx
            emit(NetworkState.Error(message = e.response.status.description))
        } catch (e: ServerResponseException) {
            // 5xx
            emit(NetworkState.Error(message = e.response.status.description))
        } catch (e: Exception) {
            emit(NetworkState.Error(message = e.message.toString()))
        }
    }
}