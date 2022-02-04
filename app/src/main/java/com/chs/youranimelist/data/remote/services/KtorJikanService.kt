package com.chs.youranimelist.data.remote.services

import android.util.Log
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.dto.AnimeDetails
import com.chs.youranimelist.util.Constant
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import javax.inject.Singleton

@Singleton
class KtorJikanService(
    private val client: HttpClient
) : JikanService {
    override suspend fun getAnimeTheme(malId: Int): NetWorkState<AnimeDetails> {
        return try {
            client.get {
                NetWorkState.Success(url("${Constant.JIKAN_API_URL}/$malId"))
            }
        } catch (e: RedirectResponseException) {
            // 3xx - response
            NetWorkState.Error(message = e.response.status.description)
        } catch (e: ClientRequestException) {
            // 4xx
            NetWorkState.Error(message = e.response.status.description)
        } catch (e: ServerResponseException) {
            // 5xx
            NetWorkState.Error(message = e.response.status.description)
        } catch (e: Exception) {
            NetWorkState.Error(message = e.message.toString())
        }
    }
}