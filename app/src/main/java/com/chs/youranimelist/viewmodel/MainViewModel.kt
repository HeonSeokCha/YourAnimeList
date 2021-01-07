package com.chs.youranimelist.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.network.repository.AnimeListRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository:AnimeListRepository): ViewModel() {
    private val _netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    val netWorkState: StateFlow<NetWorkState> = _netWorkState


    sealed class NetWorkState {
        object Success: NetWorkState()
        data class Error(val message: String): NetWorkState()
        object Loading: NetWorkState()
        object Empty: NetWorkState()
    }

    fun getPagerAnimeList(): LiveData<AnimeListQuery.ViewPager> {
        val responseLiveData: MutableLiveData<AnimeListQuery.ViewPager> = MutableLiveData()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeList().catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect{
                responseLiveData.value = it.viewPager
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }

    fun getAnimeInfo(animeId: Input<Int>):LiveData<AnimeDetailQuery.Media> {
        val responseLiveData: MutableLiveData<AnimeDetailQuery.Media> = MutableLiveData()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeInfo(animeId).catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect{
                responseLiveData.value = it.media
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }

    fun getAnimeList():LiveData<List<Any>> {
        val responseLiveData: MutableLiveData<List<Any>> = MutableLiveData()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getAnimeList().catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect {
                responseLiveData.value = listOf (
                    it.trending?.media as Any,
                    it.popular?.media as Any,
                    it.upcomming?.media as Any,
                    it.alltime?.media as Any,
                )
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }
}