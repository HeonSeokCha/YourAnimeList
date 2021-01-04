package com.chs.youranimelist.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.chs.youranimelist.network.dto.AniList
import com.chs.youranimelist.network.dto.Anime
import com.chs.youranimelist.network.dto.Data
import com.chs.youranimelist.network.querys.AnimeQuery
import com.chs.youranimelist.network.repository.AnimeListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MainViewModel(
    private val repository:AnimeListRepository): ViewModel() {
    val netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    private val responseLiveData: MutableLiveData<AniList> = MutableLiveData()
    private val query by lazy { AnimeQuery() }
    private lateinit var jsonObj: JSONObject


    sealed class NetWorkState {
        object Success: NetWorkState()
        data class Error(val message: String): NetWorkState()
        object Loading: NetWorkState()
        object Empty: NetWorkState()
    }

    fun getPagerAnimeList(): LiveData<AniList> {
        viewModelScope.launch {
            netWorkState.value = NetWorkState.Loading
            jsonObj = JSONObject().put("query", query.getAnimeList())
            val req = jsonObj.toString().toRequestBody("application/json".toMediaTypeOrNull())
            repository.getAnimeList(req).catch { e->
                netWorkState.value = NetWorkState.Error(e.toString())
            }.collect {
                responseLiveData.value = it
                netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }

    fun getTopAnimeList() = viewModelScope.launch(Dispatchers.IO) {
        netWorkState.value = NetWorkState.Loading
        jsonObj = JSONObject().put("query",query.getAnimeList("TRENDING"))
    }

    fun getTrendAnimeList() = viewModelScope.launch(Dispatchers.IO) {
        netWorkState.value = NetWorkState.Loading
        jsonObj = JSONObject().put("query",query.getAnimeList("SCORE"))
    }

    fun getAnimeInfo(animeId: String):LiveData<AniList> {
        netWorkState.value = NetWorkState.Loading
        jsonObj = JSONObject().put("query", query.getAnimeInfo(animeId))
        val req = RequestBody.create("application/json".toMediaTypeOrNull(),jsonObj.toString())
        viewModelScope.launch {
            repository.getAnimeInfo(req).catch { e->
                netWorkState.value = NetWorkState.Error(e.toString())
            }.collect {
                responseLiveData.value = it
                netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }
}