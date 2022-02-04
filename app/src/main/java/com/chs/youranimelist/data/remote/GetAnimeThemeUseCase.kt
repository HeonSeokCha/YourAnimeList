package com.chs.youranimelist.data.remote

import android.util.Log
import com.chs.youranimelist.data.remote.dto.AnimeDetails
import com.chs.youranimelist.data.remote.repository.AnimeRepository
import io.ktor.client.*
import io.ktor.client.features.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//class GetAnimeThemeUseCase @Inject constructor(
//    private val repository: AnimeRepository
//) {
//    operator fun invoke(malId: Int): Flow<NetWorkState<AnimeDetails>> = flow {
//        try {
//            emit(NetWorkState.Loading())
//            val animeDetails = repository.getAnimeOverviewTheme(malId)
//            emit(NetWorkState.Success(animeDetails))
//        } catch (e: RedirectResponseException) {
//            // 3xx - response
//            emit(NetWorkState.Error(message = e.response.status.description))
//        } catch (e: ClientRequestException) {
//            // 4xx
//            emit(NetWorkState.Error(message = e.response.status.description))
//        } catch (e: ServerResponseException) {
//            // 5xx
//            emit(NetWorkState.Error(message = e.response.status.description))
//        } catch (e: Exception) {
//            emit(NetWorkState.Error(message = e.message.toString()))
//        }
//    }
//}