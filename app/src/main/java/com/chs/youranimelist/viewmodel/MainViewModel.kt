package com.chs.youranimelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.network.querys.AnimeQuery
import com.chs.youranimelist.network.repository.AnimeListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject


class MainViewModelFactory(private val repository:AnimeListRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

class MainViewModel(repository:AnimeListRepository):ViewModel() {
    val netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    private val query by lazy { AnimeQuery() }
    private lateinit var jsonObj: JSONObject


    sealed class NetWorkState {
        object Success: NetWorkState()
        data class Error(val message: String): NetWorkState()
        object Loading: NetWorkState()
        object Empty: NetWorkState()
    }

    fun getPagerAnimeList() = viewModelScope.launch(Dispatchers.IO) {
        netWorkState.value = NetWorkState.Loading
        jsonObj = JSONObject().put("query",query.getAnimeList())
    }

    fun getTopAnimeList() = viewModelScope.launch(Dispatchers.IO) {
        netWorkState.value = NetWorkState.Loading
        jsonObj = JSONObject().put("query",query.getAnimeList("TRENDING"))
    }

    fun getTrendAnimeList() = viewModelScope.launch(Dispatchers.IO) {
        netWorkState.value = NetWorkState.Loading
        jsonObj = JSONObject().put("query",query.getAnimeList("SCORE"))
    }

    fun getAnimeInfo(animeId: String) = viewModelScope.launch(Dispatchers.IO) {
        netWorkState.value = NetWorkState.Loading
        jsonObj = JSONObject().put("query", query.getAnimeInfo(animeId))
    }
}