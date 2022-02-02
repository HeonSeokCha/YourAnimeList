package com.chs.youranimelist.network.services

import android.util.Log
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.response.AnimeDetails
import com.chs.youranimelist.util.Constant
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import javax.inject.Singleton

class JikanRestServicesImpl(
    private val client: HttpClient
) : JikanRestService {
    override suspend fun getAnimeTheme(malId: Int): NetWorkState<AnimeDetails?> {
        return try {
            client.get {
                NetWorkState.Success(url("${Constant.JIKAN_API_URL}/$malId"))
            }
        } catch (e: RedirectResponseException) {
            // 3xx - response
            Log.e("ERROR3", "Error: ${e.response.status.description}")
            NetWorkState.Error(message = e.response.status.description)
        } catch (e: ClientRequestException) {
            // 4xx
            Log.e("ERROR4", "Error: ${e.response.status.description}")
            NetWorkState.Error(message = e.response.status.description)
        } catch (e: ServerResponseException) {
            // 5xx
            Log.e("ERROR5", "Error: ${e.response.status.description}")
            NetWorkState.Error(message = e.response.status.description)
        } catch (e: Exception) {
            Log.e("ERROR", "Error: ${e.message}")
            NetWorkState.Error(message = e.message.toString())
        }
    }
}